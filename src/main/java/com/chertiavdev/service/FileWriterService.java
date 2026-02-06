package com.chertiavdev.service;

import com.chertiavdev.dto.operation.result.OperationDataResult;
import java.util.List;

public interface FileWriterService {
    void write(List<OperationDataResult> dataToWrite);
}
