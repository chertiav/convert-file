package com.chertiavdev.strategy;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.strategy.reader.ReadFileHandler;
import java.util.Map;

public class ReadFileStrategyImpl implements ReadFileStrategy {
    private static final String NO_READ_FILE_HANDLER_CONFIGURED_FOR_MODE =
            "No ReadFileHandler configured for mode: ";
    private static final String NULL_MODE_ERROR_MESSAGE = "mode must not be null";
    private final Map<Mode, ReadFileHandler<?>> readFileHandlerMap;

    public ReadFileStrategyImpl(Map<Mode, ReadFileHandler<?>> readFileHandlerMap) {
        this.readFileHandlerMap = Map.copyOf(readFileHandlerMap);

        for (Mode mode : Mode.values()) {
            if (!this.readFileHandlerMap.containsKey(mode)) {
                throw new IllegalStateException(NO_READ_FILE_HANDLER_CONFIGURED_FOR_MODE + mode);
            }
        }
    }

    @Override
    public <T extends OperationDto> ReadFileHandler<T> get(Mode mode) {
        if (mode == null) {
            throw new IllegalArgumentException(NULL_MODE_ERROR_MESSAGE);
        }

        ReadFileHandler<?> handler = readFileHandlerMap.get(mode);
        if (handler == null) {
            throw new IllegalStateException(NO_READ_FILE_HANDLER_CONFIGURED_FOR_MODE + mode);
        }

        @SuppressWarnings("unchecked")
        ReadFileHandler<T> typed = (ReadFileHandler<T>) handler;
        return typed;
    }
}
