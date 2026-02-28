package com.chertiavdev.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AppHelper {
    private static final String DOT_CHARA = ".";

    private static final String MONTH_PART_IS_NOT_A_NUMBER = "Month part is not a number: ";
    private static final char DASH_CHAR = '-';
    private static final String INVALID_DURATION_FORMAT = "Invalid duration format: ";
    private static final String HOURS_MINUTES_SECONDS_FORMAT = "%d:%02d:%02d";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(
            "0.00",
            new DecimalFormatSymbols(Locale.forLanguageTag("ru-RU"))
    );

    private AppHelper() {
    }

    public static int extractMonthFromFilename(String fileName) {
        int dashIndex = fileName.lastIndexOf(DASH_CHAR);
        int extIndex = fileName.lastIndexOf(DOT_CHARA);
        String monthPart = fileName.substring(dashIndex + 1, extIndex);

        try {
            return Integer.parseInt(monthPart);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(MONTH_PART_IS_NOT_A_NUMBER + monthPart, e);
        }
    }

    public static String formatDuration(String isoDuration) {
        return formatDuration(Duration.parse(isoDuration));
    }

    public static String formatDuration(Duration durationValue) {
        long totalSeconds = durationValue.getSeconds();

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format(HOURS_MINUTES_SECONDS_FORMAT, hours, minutes, seconds);
    }

    public static Duration parseDuration(String raw, Pattern pattern) {
        if (raw == null || raw.isBlank()) {
            return Duration.ZERO;
        }
        Matcher m = pattern.matcher(raw.trim());
        if (!m.matches()) {
            throw new IllegalArgumentException(INVALID_DURATION_FORMAT + raw);
        }
        long hours = m.group(1) != null ? Long.parseLong(m.group(1)) : 0;
        long minutes = m.group(2) != null ? Long.parseLong(m.group(2)) : 0;
        return Duration.ofHours(hours).plusMinutes(minutes);
    }

    public static double parseDecimal(String raw) {
        if (raw == null || raw.isBlank()) {
            return 0.0;
        }
        String normalized = raw.trim().replace(',', '.');
        return Double.parseDouble(normalized);
    }

    public static String formatDecimal(Double number) {
        return DECIMAL_FORMAT.format(number);
    }
}
