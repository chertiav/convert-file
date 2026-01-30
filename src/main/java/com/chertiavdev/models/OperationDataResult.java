package com.chertiavdev.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OperationDataResult {
    //#82B0FF
    private static final ZoneId zoneId = ZoneId.systemDefault();
    private static final List<String> COLOR_SUBSCRIPTION_CLEANING = List.of("#080480");
    private static final List<String> COLOR_PERIODIC_TASKS =
            List.of("#AFC713", "#82B0FF", "#AFC713");
    private static final String TYPE_SUBSCRIPTION_CLEANING = "Abonnementsrengøring";
    private static final String TYPE_PERIODIC_TASKS = "Periodiske opgaver";
    public static final String UNKNOWN_TYPE = "UNKNOWN TYPE";
    public static final int SALARY_MONTH_THRESHOLD = 12;
    public static final int SALARY_PAYDAY_DECEMBER = 18;
    public static final int DEFAULT_SALARY_PAYDAY = 19;
    public static final int DAYS_ADDED_TO_GET_NEXT_MONTH = 25;
    private LocalDate date;
    private String time;
    private String location;
    private String duration;
    private String type;
    private int salaryMonth;
    private int salaryYear;
    private String color;

    public OperationDataResult(Operation operation) {
        String[] dataTask = operation.title().split("\\R");
        LocalDate date = convertToLocalDate(operation);
        String color = operation.color();
        this.date = date;
        this.time = dataTask[0];
        this.location = dataTask[1];
        this.duration = getDuration(operation.start(), operation.end());
        this.type = getType(color);
        this.salaryMonth = getSalaryMonth(date);
        this.salaryYear = getSalaryYear(date);
        this.color = color;
    }

    private LocalDate convertToLocalDate(Operation operation) {
        return LocalDate.ofInstant(operation.start().toInstant(), zoneId);
    }

    private String getDuration(Date start, Date end) {
        LocalDateTime startDateTime = LocalDateTime.ofInstant(start.toInstant(), zoneId);
        LocalDateTime endDateTime = LocalDateTime.ofInstant(end.toInstant(), zoneId);

        return formatDuration(Duration.between(startDateTime, endDateTime).toString());
    }

    private String formatDuration(String isoDuration) {
        Duration durationValue = Duration.parse(isoDuration);
        long totalSeconds = durationValue.getSeconds();

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    private String getType(String color) {
        if (color == null) {
            return "";
        }
        if (isSubscriptionCleaning(color)) {
            return TYPE_SUBSCRIPTION_CLEANING;
        } else if (isPeriodicTasks(color)) {
            return TYPE_PERIODIC_TASKS;
        } else {
            return UNKNOWN_TYPE;
        }
    }

    private boolean isSubscriptionCleaning(String color) {
        return COLOR_SUBSCRIPTION_CLEANING
                .stream().anyMatch(color::contains);
    }

    private boolean isPeriodicTasks(String color) {
        return COLOR_PERIODIC_TASKS
                .stream().anyMatch(color::contains);
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
