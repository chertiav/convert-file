package com.chertiavdev.util;

import com.chertiavdev.models.OperationDataResult;
import java.util.List;

public final class ServiceUtils {
    private static final String JSON_EXTENSION = ".json";
    private static final char CSV_SEPARATOR = ',';
    private static final char CSV_RECORD_END = '\n';
    private static final String FILENAME_MUST_NOT_BE_NULL_OR_BLANK =
            "Filename must not be null or blank.";
    private static final String FILENAME_MUST_CONTAIN_MONTH_JSON =
            "Filename must contain '-<month>.json': ";
    public static final String MONTH_PART_IS_NOT_A_NUMBER = "Month part is not a number: ";
    public static final char DASH_CHAR = '-';

    private ServiceUtils() {
    }

    public static int extractMonthFromFilename(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException(FILENAME_MUST_NOT_BE_NULL_OR_BLANK);
        }
        int dashIndex = fileName.lastIndexOf(DASH_CHAR);
        int extIndex = fileName.lastIndexOf(JSON_EXTENSION);
        if (dashIndex < 0 || extIndex < 0 || extIndex <= dashIndex + 1) {
            throw new IllegalArgumentException(FILENAME_MUST_CONTAIN_MONTH_JSON + fileName);
        }

        String monthPart = fileName.substring(dashIndex + 1, extIndex);
        try {
            return Integer.parseInt(monthPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MONTH_PART_IS_NOT_A_NUMBER + monthPart, e);
        }
    }

    public static String formatForCsv(Object value) {
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

    public static String buildCsv(
            List<OperationDataResult> operationDataResults,
            String csvHeader
    ) {
        StringBuilder sb = new StringBuilder();
        sb.append(csvHeader).append(CSV_RECORD_END);

        for (OperationDataResult record : operationDataResults) {
            sb.append(formatForCsv(record.getDate()))
                    .append(CSV_SEPARATOR)
                    .append(formatForCsv(record.getTime()))
                    .append(CSV_SEPARATOR)
                    .append(formatForCsv(record.getLocation()))
                    .append(CSV_SEPARATOR)
                    .append(formatForCsv(record.getDuration()))
                    .append(CSV_SEPARATOR)
                    .append(formatForCsv(record.getType()))
                    .append(CSV_SEPARATOR)
                    .append(record.getSalaryMonth())
                    .append(CSV_SEPARATOR)
                    .append(record.getSalaryYear())
                    .append(CSV_SEPARATOR)
                    .append(record.getColor())
                    .append(CSV_RECORD_END);
        }

        return sb.toString();
    }
}
