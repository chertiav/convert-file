package com.chertiavdev.strategy.read;

import com.chertiavdev.dto.operation.plan.PlanOperationDto;
import com.chertiavdev.exceptions.FileNotOpenedException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;

public class PlanReadFile implements ReadFileHandler<PlanOperationDto> {
    private static final String FILE_NOT_OPENED_ERROR_MESSAGE = "Can't open the file: ";

    @Override
    public List<PlanOperationDto> read(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
        try {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new FileNotOpenedException(FILE_NOT_OPENED_ERROR_MESSAGE + fileName, e);
        }
    }
}
