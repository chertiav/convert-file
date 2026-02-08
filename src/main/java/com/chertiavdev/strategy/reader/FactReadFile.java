package com.chertiavdev.strategy.reader;

import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.exceptions.FileNotOpenedException;
import com.chertiavdev.mapper.FactOperationMapper;
import com.chertiavdev.parser.FactOperationRawParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactReadFile implements ReadFileHandler<FactOperationDto> {
    private static final String FILE_NOT_OPENED_ERROR_MESSAGE = "Can't open the file: ";
    private static final int MIN_COLUMNS_COUNT = 13;

    @Override
    public List<FactOperationDto> read(String fileName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            List<String> records = collectValidRecords(bufferedReader);

            return records.stream()
                    .map(FactOperationRawParser::parse)
                    .flatMap(Optional::stream)
                    .map(FactOperationMapper.INSTANCE::toFactOperationDto)
                    .toList();
        } catch (IOException e) {
            throw new FileNotOpenedException(FILE_NOT_OPENED_ERROR_MESSAGE + fileName, e);
        }
    }

    private List<String> collectValidRecords(
            BufferedReader bufferedReader
    ) throws IOException {
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

        return records;
    }
}
