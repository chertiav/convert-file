package com.chertiavdev.strategy.converter;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.operation.plan.PlanOperationDto;
import com.chertiavdev.dto.result.plan.PlanResultDto;
import com.chertiavdev.mapper.PlanResultMapper;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.List;

public class PlanFileConverter extends AbstractFileConverter<PlanOperationDto, PlanResultDto> {
    public PlanFileConverter(FileReaderService fileReaderService) {
        super(fileReaderService);
    }

    @Override
    protected List<PlanOperationDto> readOperations(Path filePath) {
        return fileReaderService.read(Mode.PLAN, filePath.toString());
    }

    @Override
    protected PlanResultDto mapToResult(PlanOperationDto operation) {
        return PlanResultMapper.INSTANCE.toDto(operation);
    }
}
