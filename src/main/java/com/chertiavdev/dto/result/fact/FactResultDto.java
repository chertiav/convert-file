package com.chertiavdev.dto.result.fact;

import static com.chertiavdev.export.csv.CsvFormatter.joinCsv;

import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.export.csv.CsvWritable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactResultDto extends OperationDataResult implements CsvWritable {
    private LocalDate date;
    private String time;
    private String location;
    private String duration;
    private String type;
    private int salaryMonth;
    private int salaryYear;
    private String shiftType;
    private String comment;
    private String paidTimeAmount;
    private String eveningAllowance;
    private String nightAllowance;
    private String sundayHolidayAllowance;
    private String serviceAllowance;
    private String extra;

    @Override
    public String toCsvLine() {
        return joinCsv(
                date,
                time,
                location,
                duration,
                type,
                salaryMonth,
                salaryYear,
                shiftType,
                comment,
                paidTimeAmount,
                eveningAllowance,
                nightAllowance,
                sundayHolidayAllowance,
                serviceAllowance,
                extra
        );
    }
}
