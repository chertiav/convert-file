package com.chertiavdev.service;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.operation.OperationDto;
import java.util.List;

public interface FileReaderService {
    <T extends OperationDto> List<T> read(Mode mode, String fileName);
}
