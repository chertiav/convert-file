package com.chertiavdev.stgategy.read;

import com.chertiavdev.dto.operation.OperationDto;
import java.util.List;

public interface ReadFileHandler<T extends OperationDto> {
    List<T> read(String fileName);
}
