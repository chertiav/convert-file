package com.chertiavdev.service;

import com.chertiavdev.models.Operation;
import com.chertiavdev.models.OperationDataResult;
import java.nio.file.Path;
import java.util.List;

public interface OperationFileService {
    List<Path> listInputFiles(Path inputDir);

    List<Operation> read(String fileName);

    List<OperationDataResult> filterOperationsByMonth(List<Path> inputFiles);

    boolean shouldWriteHeader(Path outputCsv);

    void write(
            String toFileName,
            List<OperationDataResult> results,
            String csvHeader,
            boolean writeHeader
    );
}
