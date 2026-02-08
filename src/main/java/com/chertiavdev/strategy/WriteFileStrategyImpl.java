package com.chertiavdev.strategy;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.strategy.writer.WriteFileHandler;
import java.util.Map;

public class WriteFileStrategyImpl implements WriteFileStrategy {
    private static final String NO_WRITE_FILE_HANDLER_CONFIGURED_FOR_MODE =
            "No WriteFileHandler configured for mode: ";
    private static final String NULL_MODE_ERROR_MESSAGE = "mode must not be null";
    private final Map<Mode, WriteFileHandler<?>> writeFileHandlerMap;

    public WriteFileStrategyImpl(Map<Mode, WriteFileHandler<?>> writeFileHandlerMap) {
        this.writeFileHandlerMap = Map.copyOf(writeFileHandlerMap);

        for (Mode mode : Mode.values()) {
            if (!this.writeFileHandlerMap.containsKey(mode)) {
                throw new IllegalStateException(NO_WRITE_FILE_HANDLER_CONFIGURED_FOR_MODE + mode);
            }
        }
    }

    @Override
    public <T extends OperationDataResult> WriteFileHandler<T> get(Mode mode) {
        if (mode == null) {
            throw new IllegalArgumentException(NULL_MODE_ERROR_MESSAGE);
        }

        WriteFileHandler<?> handler = writeFileHandlerMap.get(mode);
        if (handler == null) {
            throw new IllegalStateException(NO_WRITE_FILE_HANDLER_CONFIGURED_FOR_MODE + mode);
        }

        @SuppressWarnings("unchecked")
        WriteFileHandler<T> typed = (WriteFileHandler<T>) handler;
        return typed;
    }
}
