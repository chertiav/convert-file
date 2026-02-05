package com.chertiavdev.stgategy.converter;

import com.chertiavdev.dto.operation.OperationDataResult;
import java.nio.file.Path;
import java.util.List;

public interface FileConverterHandler {
    List<OperationDataResult> convertAllFiles(List<Path> inputFiles);
}
