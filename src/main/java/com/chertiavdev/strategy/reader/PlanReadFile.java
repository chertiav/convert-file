package com.chertiavdev.strategy.reader;

import com.chertiavdev.dto.operation.plan.PlanOperationDto;
import com.chertiavdev.exceptions.FileNotOpenedException;
import com.chertiavdev.factory.ObjectMapperFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.util.List;

public class PlanReadFile implements ReadFileHandler<PlanOperationDto> {
    private static final String FILE_NOT_OPENED_ERROR_MESSAGE = "Can't open the file: ";

    @Override
    public List<PlanOperationDto> read(String fileName) {
        File file = new File(fileName);
        try {
            return ObjectMapperFactory.create().readValue(file, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new FileNotOpenedException(FILE_NOT_OPENED_ERROR_MESSAGE + fileName, e);
        }
    }
}
