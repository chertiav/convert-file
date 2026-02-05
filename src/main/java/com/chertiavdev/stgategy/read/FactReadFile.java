package com.chertiavdev.stgategy.read;

import static com.chertiavdev.util.ServiceUtils.parseDecimal;
import static com.chertiavdev.util.ServiceUtils.parseDuration;

import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.exceptions.FileNotOpenedException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactReadFile implements ReadFileHandler<FactOperationDto> {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM-uuuu");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String FILE_NOT_OPENED_ERROR_MESSAGE = "Can't open the file: ";
    private static final Pattern DURATION_PATTERN = Pattern
            .compile("(?:(\\d+)\\s*[ht])?\\s*(?:(\\d+)\\s*m)?", Pattern.CASE_INSENSITIVE);
    private static final int MIN_COLUMNS_COUNT = 13;
    private static final String DATE_TIME_SEPARATOR = " - ";
    private static final String EXTRA_DURATION_PREFIX = "(Extra:";
    private static final String EXTRA_DURATION_PATTERN = "\\(Extra:\\s*([^)]*)\\)";

    @Override
    public List<FactOperationDto> read(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            List<String> records = new ArrayList<>();
            StringBuilder current = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) {
                    continue;
                }

                if (!current.isEmpty()) {
                    current.append(' ');
                }
                current.append(trimmed);

                String[] parts = current.toString().split("\t");
                if (parts.length >= MIN_COLUMNS_COUNT) {
                    records.add(current.toString());
                    current.setLength(0);
                }
            }

            if (!current.isEmpty()) {
                records.add(current.toString());
            }

            return records.stream()
                    .map(this::buildFactOperation)
                    .toList();
        } catch (IOException e) {
            throw new FileNotOpenedException(FILE_NOT_OPENED_ERROR_MESSAGE + fileName, e);
        }
    }

    private FactOperationDto buildFactOperation(String record) {
        String[] parts = record.split("\t");
        if (parts.length < MIN_COLUMNS_COUNT) {
            return null;
        }

        String[] dateTimeParts = parts[4].split(DATE_TIME_SEPARATOR);
        if (dateTimeParts.length < 2) {
            return null;
        }

        return createFactOperation(dateTimeParts, parts);
    }

    private FactOperationDto createFactOperation(String[] dateTimeParts, String[] parts) {
        String datePart = dateTimeParts[0].trim();
        String timePart = dateTimeParts[1].replace("Kl.", "").trim();

        ExtraDuration extraDuration = extractExtraDuration(parts[5]);

        FactOperationDto operation = new FactOperationDto();
        operation.setType(parts[0]);
        operation.setRate(parts[1]);
        operation.setLocation(parts[2]);
        operation.setShiftType(parts[3]);
        operation.setDate(LocalDate.parse(datePart, DATE_FORMATTER));
        operation.setTime(LocalTime.parse(timePart, TIME_FORMATTER));

        Duration base = parseDuration(extraDuration.baseDuration, DURATION_PATTERN);
        Duration extra = parseDuration(extraDuration.extraDuration, DURATION_PATTERN);
        operation.setDuration(base.plus(extra));

        operation.setName(parts[6]);

        String baseComment = parts[7] != null ? parts[7].trim() : "";
        String extraComment = extraDuration.commentSuffix != null
                ? extraDuration.commentSuffix.trim()
                : "";
        operation.setComment(mergeComments(baseComment, extraComment));

        operation.setPaidTimeAmount(parseDecimal(parts[8]));
        operation.setEveningAllowance(parseDecimal(parts[9]));
        operation.setNightAllowance(parseDecimal(parts[10]));
        operation.setSundayHolidayAllowance(parseDecimal(parts[11]));
        operation.setServiceAllowance(parseDecimal(parts[12]));
        operation.setExtra(parts.length > MIN_COLUMNS_COUNT ? parseDecimal(parts[13]) : 0.0);
        return operation;
    }

    private String mergeComments(String baseComment, String extraComment) {
        if (extraComment.isEmpty()) {
            return baseComment;
        }
        return baseComment.isEmpty()
                ? extraComment
                : baseComment + " " + extraComment;
    }

    private record ExtraDuration(String baseDuration, String extraDuration, String commentSuffix) {
    }

    private ExtraDuration extractExtraDuration(String raw) {
        if (raw == null) {
            return new ExtraDuration("", "", "");
        }

        String base = raw;
        String extra = "";
        String comment = "";

        int extraIndex = raw.indexOf(EXTRA_DURATION_PREFIX);
        if (extraIndex >= 0) {
            base = raw.substring(0, extraIndex).trim();
            comment = raw.substring(extraIndex).trim();

            Matcher m = Pattern.compile(EXTRA_DURATION_PATTERN, Pattern.CASE_INSENSITIVE)
                    .matcher(comment);
            if (m.find()) {
                extra = m.group(1).trim();
            }
        }

        return new ExtraDuration(base, extra, comment);
    }

}
