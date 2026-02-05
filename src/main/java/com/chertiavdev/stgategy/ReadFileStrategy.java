package com.chertiavdev.stgategy;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.stgategy.read.ReadFileHandler;

public interface ReadFileStrategy {
    ReadFileHandler<? extends OperationDto> get(Mode mode);
}
