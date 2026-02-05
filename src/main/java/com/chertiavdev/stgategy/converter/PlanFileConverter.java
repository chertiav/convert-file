package com.chertiavdev.stgategy.converter;

import static com.chertiavdev.util.ServiceUtils.extractMonthFromFilename;

import com.chertiavdev.dto.operation.OperationDataResult;
import com.chertiavdev.dto.operation.PlanOperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class PlanFileConverter implements FileConverterHandler {
    private final FileReaderService fileReaderService;

    public PlanFileConverter(FileReaderService fileReaderService) {
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
        List<PlanOperationDto> planOperations = fileReaderService
                .read(Mode.PLAN, filePath.toString());
        return convert(planOperations, monthIdentifier).stream();
    }

    private Comparator<? super OperationDataResult> operationDateTimeComparator() {
        return Comparator.comparing(OperationDataResult::getDate);
    }

    private List<OperationDataResult> convert(
            List<PlanOperationDto> planOperations,
            int monthIdentifier
    ) {
        return planOperations.stream()
                .map(OperationDataResult::new)
                .filter(operationDataResult ->
                        operationDataResult.getDate().getMonthValue() == monthIdentifier)
                .toList();
    }
}
