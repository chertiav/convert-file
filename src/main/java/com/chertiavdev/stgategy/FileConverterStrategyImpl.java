package com.chertiavdev.stgategy;

import com.chertiavdev.enums.Mode;
import com.chertiavdev.stgategy.converter.FileConverterHandler;
import java.util.Map;

public class FileConverterStrategyImpl implements FileConverterStrategy {
    public static final String NO_FILE_CONVERTER_CONFIGURED_FOR_MODE =
            "No file converter configured for mode: ";
    private final Map<Mode, FileConverterHandler> fileConverterHandlerMap;

    public FileConverterStrategyImpl(Map<Mode, FileConverterHandler> fileConverterHandlerMap) {
        this.fileConverterHandlerMap = fileConverterHandlerMap;
    }

    @Override
    public FileConverterHandler get(Mode mode) {
        FileConverterHandler handler = fileConverterHandlerMap.get(mode);
        if (handler == null) {
            throw new IllegalArgumentException(NO_FILE_CONVERTER_CONFIGURED_FOR_MODE + mode);
        }
        return handler;
    }
}
