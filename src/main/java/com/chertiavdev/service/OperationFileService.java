package com.chertiavdev.service;

import com.chertiavdev.models.Operation;
import com.chertiavdev.models.OperationDataResult;
import java.util.List;

public interface OperationFileService {
    List<Operation> read(String fileName);

    List<OperationDataResult> convert(List<Operation> operations, int monthIdentifier);

    void write(String toFileName, List<OperationDataResult> results, String csvHeader);
}
