package com.chertiavdev.strategy;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.strategy.read.ReadFileHandler;

public interface ReadFileStrategy {
    <T extends OperationDto> ReadFileHandler<T> get(Mode mode);
}
