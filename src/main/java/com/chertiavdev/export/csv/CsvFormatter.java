package com.chertiavdev.export.csv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class CsvFormatter {
    private static final char CSV_SEPARATOR = ',';
    private static final String CSV_RECORD_END = "\r\n";

    private CsvFormatter() {
    }

    public static String joinCsv(Object... values) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            sb.append(formatForCsv(values[i]));
            if (i < values.length - 1) {
                sb.append(CSV_SEPARATOR);
            }
        }
        sb.append(CSV_RECORD_END);
        return sb.toString();
    }

    public static String formatForCsv(Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof LocalDate date) {
            return date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }

        String stringValue = String.valueOf(value);

        // normalize raw input value for safe CSV output (RFC 4180)
        if (stringValue.contains("\"")
                || stringValue.contains(",")
                || stringValue.contains("\n")
                || stringValue.contains("\r")) {
            stringValue = stringValue.replace("\"", "\"\"");
            return "\"" + stringValue + "\"";
        }

        return stringValue;
    }
}
