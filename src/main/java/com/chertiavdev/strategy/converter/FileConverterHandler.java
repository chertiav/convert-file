package com.chertiavdev.strategy.converter;

import com.chertiavdev.dto.result.OperationDataResult;
import java.nio.file.Path;
import java.util.List;

public interface FileConverterHandler {
    List<OperationDataResult> convertAllFiles(List<Path> inputFiles);
}
