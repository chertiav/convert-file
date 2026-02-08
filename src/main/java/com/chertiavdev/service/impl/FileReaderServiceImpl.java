package com.chertiavdev.service.impl;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.service.FileReaderService;
import com.chertiavdev.strategy.ReadFileStrategy;
import com.chertiavdev.strategy.reader.ReadFileHandler;
import java.util.List;

public class FileReaderServiceImpl implements FileReaderService {
    private final ReadFileStrategy readFileStrategy;

    public FileReaderServiceImpl(ReadFileStrategy readFileStrategy) {
        this.readFileStrategy = readFileStrategy;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OperationDto> List<T> read(Mode mode, String fileName) {
        ReadFileHandler<?> fileReadHandler = readFileStrategy.get(mode);
        ReadFileHandler<T> specificReadHandler = (ReadFileHandler<T>) fileReadHandler;
        return specificReadHandler.read(fileName);
    }
}
