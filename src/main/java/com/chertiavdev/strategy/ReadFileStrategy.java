package com.chertiavdev.strategy;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.strategy.read.ReadFileHandler;

public interface ReadFileStrategy {
    ReadFileHandler<? extends OperationDto> get(Mode mode);
}
