package com.chertiavdev.strategy;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.strategy.converter.FileConverterHandler;

public interface FileConverterStrategy {
    <T extends OperationDataResult> FileConverterHandler<T> get(Mode mode);
}
