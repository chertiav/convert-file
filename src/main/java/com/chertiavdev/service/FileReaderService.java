package com.chertiavdev.service;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import java.util.List;

public interface FileReaderService {
    <T extends OperationDto> List<T> read(Mode mode, String fileName);
}
