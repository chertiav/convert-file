package com.chertiavdev.service;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
import java.nio.file.Path;
import java.util.List;

public interface FileConverterService {
    <T extends OperationDataResult> List<T> convertAllFiles(Mode mode, List<Path> inputFiles);
}
