package com.chertiavdev.service;

import com.chertiavdev.dto.operation.OperationDataResult;
import com.chertiavdev.enums.Mode;
import java.nio.file.Path;
import java.util.List;

public interface FileConverterService {
    List<OperationDataResult> convertAllFiles(Mode mode, List<Path> inputFiles);
}
