package com.chertiavdev.strategy.converter;

import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileReaderService;

public class FactFileConverter extends AbstractFileConverter<FactOperationDto> {
    public FactFileConverter(FileReaderService fileReaderService) {
        super(fileReaderService);
    }

    @Override
    protected Mode mode() {
        return Mode.FACT;
    }
}
