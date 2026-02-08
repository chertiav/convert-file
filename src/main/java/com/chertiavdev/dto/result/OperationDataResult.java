package com.chertiavdev.dto.result;

import static com.chertiavdev.util.ServiceUtils.formatDuration;

import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.dto.operation.plan.PlanOperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.enums.OperationType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OperationDataResult {
    private static final int SALARY_MONTH_THRESHOLD = 12;
    private static final int SALARY_PAYDAY_DECEMBER = 18;
    private static final int DEFAULT_SALARY_PAYDAY = 19;
    private static final int DAYS_ADDED_TO_GET_NEXT_MONTH = 25;
    private static final char CSV_SEPARATOR = ',';
    private static final char CSV_RECORD_END = '\n';
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private String mode;
    private LocalDate date;
    private String time;
    private String location;
    private String duration;
    private String type;
    private int salaryMonth;
    private int salaryYear;
    private String color;
    private String shiftType;
    private String comment;
    private Double paidTimeAmount;
    private Double eveningAllowance;
    private Double nightAllowance;
    private Double sundayHolidayAllowance;
    private Double serviceAllowance;
    private Double extra;

    public OperationDataResult(PlanOperationDto planOperation) {
        String[] dataTask = planOperation.getTitle().split("\\R");
        LocalDate date = convertToLocalDate(planOperation);
        String color = planOperation.getColor();
        this.time = dataTask[0];
        this.date = date;
        this.color = color;
        this.location = dataTask[1];
        this.mode = Mode.PLAN.name().toLowerCase();
        this.duration = getDuration(planOperation.getStart(), planOperation.getEnd());
        this.type = OperationType.fromColor(color).getType();
        this.salaryMonth = getSalaryMonth(date);
        this.salaryYear = getSalaryYear(date);
        this.shiftType = "Fast";
        this.comment = "";
        this.paidTimeAmount = 0.0;
        this.eveningAllowance = 0.0;
        this.nightAllowance = 0.0;
        this.sundayHolidayAllowance = 0.0;
        this.serviceAllowance = 0.0;
        this.extra = 0.0;
    }

    public OperationDataResult(FactOperationDto factOperationDto) {
        this.mode = Mode.FACT.name().toLowerCase();
        this.date = factOperationDto.getDate();
        this.time = factOperationDto.getTime().toString();
        this.location = factOperationDto.getLocation();
        this.duration = formatDuration(factOperationDto.getDuration());
        this.type = factOperationDto.getType();
        this.salaryMonth = date.getMonthValue();
        this.salaryYear = date.getYear();
        this.color = "";
        this.shiftType = factOperationDto.getShiftType();
        this.comment = factOperationDto.getComment();
        this.paidTimeAmount = factOperationDto.getPaidTimeAmount();
        this.eveningAllowance = factOperationDto.getEveningAllowance();
        this.nightAllowance = factOperationDto.getNightAllowance();
        this.sundayHolidayAllowance = factOperationDto.getSundayHolidayAllowance();
        this.serviceAllowance = factOperationDto.getServiceAllowance();
        this.extra = factOperationDto.getExtra();
    }

    public String toCsvLine() {
        return formatForCsv(date)
                + CSV_SEPARATOR
                + formatForCsv(time)
                + CSV_SEPARATOR
                + formatForCsv(location)
                + CSV_SEPARATOR
                + formatForCsv(duration)
                + CSV_SEPARATOR
                + formatForCsv(type)
                + CSV_SEPARATOR
                + salaryMonth
                + CSV_SEPARATOR
                + salaryYear
                + CSV_SEPARATOR
                + color
                + CSV_RECORD_END;
    }

    private String formatForCsv(Object value) {
        if (value == null) {
            return "";
        }
        String stringValue = String.valueOf(value);
        if (stringValue.contains("\"")
                || stringValue.contains(",")
                || stringValue.contains("\n")
                || stringValue.contains("\r")
        ) {
            stringValue = stringValue.replace("\"", "\"\"");
            return "\"" + stringValue + "\"";
        }
        return stringValue;
    }

    private LocalDate convertToLocalDate(PlanOperationDto planOperation) {
        return planOperation.getStart()
                .atZone(ZONE_ID)
                .toLocalDate();
    }

    private String getDuration(LocalDateTime start, LocalDateTime end) {
        return formatDuration(Duration.between(start, end));
    }

    private int getSalaryMonth(LocalDate date) {
        int monthValue = date.getMonthValue();
        int thresholdDay = monthValue == SALARY_MONTH_THRESHOLD
                ? SALARY_PAYDAY_DECEMBER
                : DEFAULT_SALARY_PAYDAY;

        return date.getDayOfMonth() <= thresholdDay
                ? monthValue
                : (date.plusDays(DAYS_ADDED_TO_GET_NEXT_MONTH)).getMonthValue();
    }

    private int getSalaryYear(LocalDate date) {
        int monthValue = date.getMonthValue();
        int thresholdDay = monthValue == SALARY_MONTH_THRESHOLD
                ? SALARY_PAYDAY_DECEMBER
                : DEFAULT_SALARY_PAYDAY;

        return date.getDayOfMonth() <= thresholdDay
                ? date.getYear()
                : (date.plusDays(DAYS_ADDED_TO_GET_NEXT_MONTH)).getYear();
    }
}
