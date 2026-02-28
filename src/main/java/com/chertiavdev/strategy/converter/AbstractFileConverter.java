package com.chertiavdev.strategy.converter;

import static com.chertiavdev.util.AppHelper.extractMonthFromFilename;

import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.service.FileReaderService;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public abstract class AbstractFileConverter<O, R extends OperationDataResult>
        implements FileConverterHandler<R> {
    protected final FileReaderService fileReaderService;

    protected AbstractFileConverter(FileReaderService fileReaderService) {
        this.fileReaderService = fileReaderService;
    }

    protected abstract List<O> readOperations(Path filePath);

    protected abstract R mapToResult(O operation);

    @Override
    public List<R> convertAllFiles(List<Path> inputFiles) {
        return inputFiles.stream()
                .flatMap(this::extractAndConvertFromFile)
                .sorted(Comparator.comparing(OperationDataResult::getDate))
                .toList();
    }

    private Stream<R> extractAndConvertFromFile(Path filePath) {
        int monthIdentifier = extractMonthFromFilename(filePath.getFileName().toString());
        List<O> operations = readOperations(filePath);

        return operations.stream()
                .map(this::mapToResult)
                .filter(result -> result.getDate() != null
                        && result.getDate().getMonthValue() == monthIdentifier);
    }
}
