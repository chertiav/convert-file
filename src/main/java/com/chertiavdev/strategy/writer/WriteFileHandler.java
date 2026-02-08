package com.chertiavdev.strategy.writer;

import com.chertiavdev.dto.result.OperationDataResult;
import java.util.List;

public interface WriteFileHandler<T extends OperationDataResult> {
    void write(List<T> data, String filePath);
}
