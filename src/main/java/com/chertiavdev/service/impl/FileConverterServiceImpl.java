package com.chertiavdev.service.impl;

import com.chertiavdev.dto.operation.OperationDataResult;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileConverterService;
import com.chertiavdev.strategy.FileConverterStrategy;
import com.chertiavdev.strategy.converter.FileConverterHandler;
import java.nio.file.Path;
import java.util.List;

public class FileConverterServiceImpl implements FileConverterService {
    private final FileConverterStrategy fileConverterStrategy;

    public FileConverterServiceImpl(FileConverterStrategy fileConverterStrategy) {
        this.fileConverterStrategy = fileConverterStrategy;
    }

    @Override
    public List<OperationDataResult> convertAllFiles(Mode mode, List<Path> inputFiles) {
        FileConverterHandler fileConverterHandler = fileConverterStrategy.get(mode);
        return fileConverterHandler.convertAllFiles(inputFiles);
    }
}
