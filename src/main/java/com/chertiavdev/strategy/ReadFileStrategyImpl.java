package com.chertiavdev.strategy;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.strategy.read.ReadFileHandler;
import java.util.Map;

public class ReadFileStrategyImpl implements ReadFileStrategy {
    public static final String NO_READ_FILE_HANDLER_CONFIGURED_FOR_MODE =
            "No ReadFileHandler configured for mode: ";
    private final Map<Mode, ReadFileHandler<? extends OperationDto>> readFileHandlerMap;

    public ReadFileStrategyImpl(
            Map<Mode, ReadFileHandler<? extends OperationDto>> readFileHandlerMap
    ) {
        this.readFileHandlerMap = readFileHandlerMap;
    }

    @Override
    public ReadFileHandler<? extends OperationDto> get(Mode mode) {
        ReadFileHandler<? extends OperationDto> handler = readFileHandlerMap.get(mode);
        if (handler == null) {
            throw new IllegalArgumentException(NO_READ_FILE_HANDLER_CONFIGURED_FOR_MODE + mode);
        }
        return handler;
    }
}
