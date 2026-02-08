package com.chertiavdev.strategy.writer;

import com.chertiavdev.dto.result.plan.PlanResultDto;
import com.chertiavdev.exceptions.FileNotWrittenException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PlanWriteFile implements WriteFileHandler<PlanResultDto> {
    private static final String DATA_FOR_WRITING_CANNOT_BE_NULL =
            "Data for writing cannot be null or empty: ";
    private static final String FILE_NOT_WRITTEN_ERROR_MESSAGE =
            "Can't write data to the file: ";

    @Override
    public void write(List<PlanResultDto> data, String filePath) {
        validateDataToWrite(data);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (PlanResultDto planResultDto : data) {
                bufferedWriter.write(planResultDto.toCsvLine());
            }
        } catch (IOException e) {
            throw new FileNotWrittenException(FILE_NOT_WRITTEN_ERROR_MESSAGE + filePath, e);
        }
    }

    private void validateDataToWrite(List<PlanResultDto> dataToWrite) {
        if (dataToWrite == null || dataToWrite.isEmpty()) {
            throw new IllegalArgumentException(DATA_FOR_WRITING_CANNOT_BE_NULL);
        }
    }
}
