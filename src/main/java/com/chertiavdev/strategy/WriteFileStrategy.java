package com.chertiavdev.strategy;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.strategy.writer.WriteFileHandler;

public interface WriteFileStrategy {
    <T extends OperationDataResult> WriteFileHandler<T> get(Mode mode);
}
