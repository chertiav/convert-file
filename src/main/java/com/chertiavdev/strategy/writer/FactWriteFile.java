package com.chertiavdev.strategy.writer;

import com.chertiavdev.dto.result.fact.FactResultDto;
import com.chertiavdev.exceptions.FileNotWrittenException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FactWriteFile implements WriteFileHandler<FactResultDto> {
    private static final String DATA_FOR_WRITING_CANNOT_BE_NULL =
            "Data for writing cannot be null or empty: ";
    private static final String FILE_NOT_WRITTEN_ERROR_MESSAGE =
            "Can't write data to the file: ";

    @Override
    public void write(List<FactResultDto> data, String filePath) {
        validateDataToWrite(data);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (FactResultDto factResultDto : data) {
                bufferedWriter.write(factResultDto.toCsvLine());
            }
        } catch (IOException e) {
            throw new FileNotWrittenException(FILE_NOT_WRITTEN_ERROR_MESSAGE + filePath, e);
        }
    }

    private void validateDataToWrite(List<FactResultDto> dataToWrite) {
        if (dataToWrite == null || dataToWrite.isEmpty()) {
            throw new IllegalArgumentException(DATA_FOR_WRITING_CANNOT_BE_NULL);
        }
    }
}
