package com.chertiavdev.strategy;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.strategy.converter.FileConverterHandler;
import java.util.Map;

public class FileConverterStrategyImpl implements FileConverterStrategy {
    private static final String NULL_MODE_ERROR_MESSAGE = "mode must not be null";
    private static final String NO_FILE_CONVERTER_CONFIGURED_FOR_MODE =
            "No file converter configured for mode: ";
    private final Map<Mode, FileConverterHandler<?>> fileConverterHandlerMap;

    public FileConverterStrategyImpl(Map<Mode, FileConverterHandler<?>> fileConverterHandlerMap) {
        this.fileConverterHandlerMap = fileConverterHandlerMap;
    }

    @Override
    public <T extends OperationDataResult> FileConverterHandler<T> get(Mode mode) {
        if (mode == null) {
            throw new IllegalArgumentException(NULL_MODE_ERROR_MESSAGE);
        }

        FileConverterHandler<?> handler = fileConverterHandlerMap.get(mode);
        if (handler == null) {
            throw new IllegalArgumentException(NO_FILE_CONVERTER_CONFIGURED_FOR_MODE + mode);
        }
        @SuppressWarnings("unchecked")
        FileConverterHandler<T> typed = (FileConverterHandler<T>) handler;

        return typed;
    }
}
