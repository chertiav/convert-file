package com.chertiavdev.models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class OperationDataResult {
    private static final ZoneId zoneId = ZoneId.systemDefault();
    private static final String COLOR_SUBSCRIPTION_CLEANING = "#080480";
    private static final String COLOR_PERIODIC_TASKS = "#AFC713";
    private static final String TYPE_SUBSCRIPTION_CLEANING = "Abonnementsrengøring";
    private static final String TYPE_PERIODIC_TASKS = "Periodiske opgaver";
    public static final String UNKNOWN_TYPE = "UNKNOWN TYPE";
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
        this.salaryMonth = date.getMonthValue();
        this.salaryYear = date.getYear();
        this.color = color;
    }

    private static LocalDate convertToLocalDate(Operation operation) {
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
        if (color.contains(COLOR_SUBSCRIPTION_CLEANING)) {
            return TYPE_SUBSCRIPTION_CLEANING;
        } else if (color.contains(COLOR_PERIODIC_TASKS)) {
            return TYPE_PERIODIC_TASKS;
        } else {
            return UNKNOWN_TYPE;
        }
    }
}
