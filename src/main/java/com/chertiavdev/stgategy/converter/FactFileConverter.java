package com.chertiavdev.stgategy.converter;

import static com.chertiavdev.util.ServiceUtils.extractMonthFromFilename;

import com.chertiavdev.dto.operation.OperationDataResult;
import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FactFileConverter implements FileConverterHandler {
    private final FileReaderService fileReaderService;

    public FactFileConverter(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    @Override
    public List<OperationDataResult> convertAllFiles(List<Path> inputFiles) {
        return inputFiles.stream()
                .flatMap(this::extractAndConvertFromFile)
                .sorted(operationDateTimeComparator())
                .toList();
    }

    private Stream<OperationDataResult> extractAndConvertFromFile(Path filePath) {
        int monthIdentifier = extractMonthFromFilename(filePath.getFileName().toString());
        List<FactOperationDto> operations = fileReaderService
                .read(Mode.FACT, filePath.toString());
        return convert(operations, monthIdentifier).stream();
    }

    private Comparator<? super OperationDataResult> operationDateTimeComparator() {
        return Comparator.comparing(OperationDataResult::getDate);
    }

    private List<OperationDataResult> convert(
            List<FactOperationDto> operations,
            int monthIdentifier
    ) {
        return operations.stream()
                .map(OperationDataResult::new)
                .filter(operationDataResult ->
                        operationDataResult.getDate().getMonthValue() == monthIdentifier)
                .toList();
    }
}
