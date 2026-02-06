package com.chertiavdev.strategy.converter;

import com.chertiavdev.dto.operation.plan.PlanOperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileReaderService;

public class PlanFileConverter extends AbstractFileConverter<PlanOperationDto> {
    public PlanFileConverter(FileReaderService fileReaderService) {
        super(fileReaderService);
    }

    @Override
    protected Mode mode() {
        return Mode.PLAN;
    }
}
