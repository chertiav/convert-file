package com.chertiavdev.strategy.converter;

import static com.chertiavdev.util.DateTimeHelper.extractMonthFromFilename;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.operation.plan.PlanOperationDto;
import com.chertiavdev.dto.result.plan.PlanResultDto;
import com.chertiavdev.mapper.PlanResultMapper;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class PlanFileConverter implements FileConverterHandler<PlanResultDto> {
    private final FileReaderService fileReaderService;

    public PlanFileConverter(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @Override
    public List<PlanResultDto> convertAllFiles(List<Path> inputFiles) {
        return inputFiles.stream()
                .flatMap(this::extractAndConvertFromFile)
                .sorted(Comparator.comparing(PlanResultDto::getDate))
                .toList();
    }

    private Stream<PlanResultDto> extractAndConvertFromFile(Path filePath) {
        int monthIdentifier = extractMonthFromFilename(filePath.getFileName().toString());
        List<PlanOperationDto> operations = fileReaderService.read(Mode.PLAN, filePath.toString());

        return operations.stream()
                .map(PlanResultMapper.INSTANCE::toDto)
                .filter(operationDataResult ->
                        operationDataResult.getDate().getMonthValue() == monthIdentifier);
    }
}
