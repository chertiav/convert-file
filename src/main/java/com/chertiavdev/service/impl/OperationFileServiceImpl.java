package com.chertiavdev.service.impl;

import static com.chertiavdev.util.ServiceUtils.buildCsv;

import com.chertiavdev.exceptions.FileNotOpenedException;
import com.chertiavdev.exceptions.FileNotWrittenException;
import com.chertiavdev.models.Operation;
import com.chertiavdev.models.OperationDataResult;
import com.chertiavdev.service.OperationFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OperationFileServiceImpl implements OperationFileService {
    private static final TypeReference<List<Operation>> OPERATION_LIST_TYPE =
            new TypeReference<>() {
            };
    private static final String FILE_NOT_OPENED_ERROR_MESSAGE = "Can't open the file: ";
    private static final String FILE_NOT_WRITTEN_ERROR_MESSAGE = "Can't write data to the file: ";

    @Override
    public List<Operation> read(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
        try {
            return objectMapper.readValue(file, OPERATION_LIST_TYPE);
        } catch (Exception e) {
            throw new FileNotOpenedException(FILE_NOT_OPENED_ERROR_MESSAGE + fileName, e);
        }
    }

    @Override
    public List<OperationDataResult> convert(List<Operation> operations, int monthIdentifier) {
        return operations.stream()
                .map(OperationDataResult::new)
                .filter(operationDataResult ->
                        operationDataResult.getSalaryMonth() == monthIdentifier)
                .toList();
    }

    @Override
    public void write(String toFileName, List<OperationDataResult> results, String csvHeader) {
        String csvFromData = buildCsv(results, csvHeader);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            bufferedWriter.write(csvFromData);
        } catch (IOException e) {
            throw new FileNotWrittenException(FILE_NOT_WRITTEN_ERROR_MESSAGE + toFileName, e);
        }
    }
}
