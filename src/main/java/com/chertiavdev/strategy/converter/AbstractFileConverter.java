package com.chertiavdev.strategy.converter;

import static com.chertiavdev.util.ServiceUtils.extractMonthFromFilename;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.dto.result.OperationDataResultFactory;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractFileConverter<T extends OperationDto>
        implements FileConverterHandler {

    protected final FileReaderService fileReaderService;

    protected AbstractFileConverter(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    protected abstract Mode mode();

    @Override
    public List<OperationDataResult> convertAllFiles(List<Path> inputFiles) {
        return inputFiles.stream()
                .flatMap(this::extractAndConvertFromFile)
                .sorted(Comparator.comparing(OperationDataResult::getDate))
                .toList();
    }

    private Stream<OperationDataResult> extractAndConvertFromFile(Path filePath) {
        int monthIdentifier = extractMonthFromFilename(filePath.getFileName().toString());
        List<T> operations = fileReaderService.read(mode(), filePath.toString());

        return operations.stream()
                .map(OperationDataResultFactory::from)
                .filter(operationDataResult ->
                        operationDataResult.getDate().getMonthValue() == monthIdentifier);
    }
}
