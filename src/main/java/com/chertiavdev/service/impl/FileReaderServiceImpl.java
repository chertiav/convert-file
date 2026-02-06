package com.chertiavdev.service.impl;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileReaderService;
import com.chertiavdev.strategy.ReadFileStrategy;
import com.chertiavdev.strategy.read.ReadFileHandler;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    private final ReadFileStrategy readFileStrategy;

    public FileReaderServiceImpl(ReadFileStrategy readFileStrategy) {
        this.readFileStrategy = readFileStrategy;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OperationDto> List<T> read(Mode mode, String fileName) {
        ReadFileHandler<? extends OperationDto> handler = readFileStrategy.get(mode);
        return (List<T>) handler.read(fileName);
    }
}
