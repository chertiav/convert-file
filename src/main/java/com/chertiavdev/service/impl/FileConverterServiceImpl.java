package com.chertiavdev.service.impl;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
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

    @SuppressWarnings("unchecked")
    @Override
    public <T extends OperationDataResult> List<T> convertAllFiles(
            Mode mode,
            List<Path> inputFiles
    ) {
        FileConverterHandler<?> fileConverterHandler = fileConverterStrategy.get(mode);
        FileConverterHandler<T> specificConverterHandler =
                (FileConverterHandler<T>) fileConverterHandler;
        return specificConverterHandler.convertAllFiles(inputFiles);
    }
}
