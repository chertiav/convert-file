package com.chertiavdev.strategy.converter;

import static com.chertiavdev.util.DateTimeHelper.extractMonthFromFilename;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.dto.result.fact.FactResultDto;
import com.chertiavdev.mapper.FactResultMapper;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FactFileConverter implements FileConverterHandler<FactResultDto> {
    private final FileReaderService fileReaderService;

    public FactFileConverter(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @Override
    public List<FactResultDto> convertAllFiles(List<Path> inputFiles) {
        return inputFiles.stream()
                .flatMap(this::extractAndConvertFromFile)
                .sorted(Comparator.comparing(FactResultDto::getDate))
                .toList();
    }

    private Stream<FactResultDto> extractAndConvertFromFile(Path filePath) {
        int monthIdentifier = extractMonthFromFilename(filePath.getFileName().toString());
        List<FactOperationDto> operations = fileReaderService.read(Mode.FACT, filePath.toString());

        return operations.stream()
                .map(FactResultMapper.INSTANCE::toDto)
                .filter(operationDataResult ->
                        operationDataResult.getDate().getMonthValue() == monthIdentifier);
    }
}
