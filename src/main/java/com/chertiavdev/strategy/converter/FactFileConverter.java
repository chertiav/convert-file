package com.chertiavdev.strategy.converter;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.dto.result.fact.FactResultDto;
import com.chertiavdev.mapper.FactResultMapper;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.List;

public class FactFileConverter extends AbstractFileConverter<FactOperationDto, FactResultDto> {
    public FactFileConverter(FileReaderService fileReaderService) {
        super(fileReaderService);
    }

    @Override
    protected List<FactOperationDto> readOperations(Path filePath) {
        return fileReaderService.read(Mode.FACT, filePath.toString());
    }

    @Override
    protected FactResultDto mapToResult(FactOperationDto operation) {
        return FactResultMapper.INSTANCE.toDto(operation);
    }
}

