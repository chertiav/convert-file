package com.chertiavdev.parser;

import com.chertiavdev.dto.operation.fact.FactOperationRawDto;
import java.util.Optional;

public class FactOperationRawParser {
    private static final int MIN_COLUMNS_COUNT = 13;
    private static final String DATE_TIME_SEPARATOR = " - ";
    private static final int DATE_PART_INDEX = 0;
    private static final int TIME_PART_INDEX = 1;
    private static final String TAB_DELIMITER = "\t";
    private static final String TIME_INDICATOR = "Kl.";
    private static final String EMPTY_STRING = "";
    private static final int DATE_TIME_PART_INDEX = 4;
    private static final int MIN_PARTS_REQUIRED = 2;
    private static final String DEFAULT_VALUE = "0.0";

    private FactOperationRawParser() {
    }

    public static Optional<FactOperationRawDto> parse(String record) {
        String[] parts = record.split(TAB_DELIMITER);
        if (parts.length < MIN_COLUMNS_COUNT) {
            return Optional.empty();
        }

        String[] dateTimeParts = parts[DATE_TIME_PART_INDEX].split(DATE_TIME_SEPARATOR);
        if (dateTimeParts.length < MIN_PARTS_REQUIRED) {
            return Optional.empty();
        }

        String date = dateTimeParts[DATE_PART_INDEX].trim();
        String time = dateTimeParts[TIME_PART_INDEX].replace(TIME_INDICATOR, EMPTY_STRING).trim();

        String extra = parts.length > MIN_COLUMNS_COUNT ? parts[13] : DEFAULT_VALUE;

        return Optional.of(new FactOperationRawDto(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                date,
                time,
                parts[5],
                parts[6],
                parts[7],
                parts[8],
                parts[9],
                parts[10],
                parts[11],
                parts[12],
                extra
        ));
    }
}
