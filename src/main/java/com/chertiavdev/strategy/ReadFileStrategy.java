package com.chertiavdev.strategy;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.strategy.reader.ReadFileHandler;

public interface ReadFileStrategy {
    <T extends OperationDto> ReadFileHandler<T> get(Mode mode);
}
