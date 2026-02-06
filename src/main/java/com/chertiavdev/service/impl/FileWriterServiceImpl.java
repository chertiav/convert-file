package com.chertiavdev.service.impl;

import com.chertiavdev.dto.operation.result.OperationDataResult;
import com.chertiavdev.exceptions.FileNotWrittenException;
import com.chertiavdev.service.FileWriterService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterServiceImpl implements FileWriterService {
    private static final String FILE_NOT_WRITTEN_ERROR_MESSAGE = "Can't write data to the file: ";
    private static final String DATA_FOR_WRITING_CANNOT_BE_NULL =
            "Data for writing cannot be null or empty: ";
    private final String filePath;

    public FileWriterServiceImpl(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(List<OperationDataResult> dataToWrite) {
        validateDataToWrite(dataToWrite);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (OperationDataResult operationDataResult : dataToWrite) {
                bufferedWriter.write(operationDataResult.toCsvLine());
            }
        } catch (IOException e) {
            throw new FileNotWrittenException(FILE_NOT_WRITTEN_ERROR_MESSAGE + filePath, e);
        }
    }

    private void validateDataToWrite(List<OperationDataResult> dataToWrite) {
        if (dataToWrite == null || dataToWrite.isEmpty()) {
            throw new IllegalArgumentException(DATA_FOR_WRITING_CANNOT_BE_NULL);
        }
    }
}
