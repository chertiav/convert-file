package com.chertiavdev.service;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
import java.util.List;

public interface FileWriterService {
    <T extends OperationDataResult> void write(Mode mode, List<T> data);
}
