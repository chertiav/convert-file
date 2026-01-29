package com.chertiavdev.service.impl;

import static com.chertiavdev.util.ServiceUtils.buildCsv;
import static com.chertiavdev.util.ServiceUtils.extractMonthFromFilename;

import com.chertiavdev.exceptions.FileNotOpenedException;
import com.chertiavdev.exceptions.FileNotWrittenException;
import com.chertiavdev.models.Operation;
import com.chertiavdev.models.OperationDataResult;
import com.chertiavdev.service.OperationFileService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class OperationFileServiceImpl implements OperationFileService {
    private static final TypeReference<List<Operation>> OPERATION_LIST_TYPE =
            new TypeReference<>() {
            };
    private static final String FILE_NOT_OPENED_ERROR_MESSAGE = "Can't open the file: ";
    private static final String FILE_NOT_WRITTEN_ERROR_MESSAGE = "Can't write data to the file: ";
    private static final String CAN_T_LIST_FILES_IN_THE_DIRECTORY =
            "Can't list files in the directory: ";
    private static final Pattern INPUT_FILE_PATTERN = Pattern.compile("\\d{4}-\\d{2}\\.json");

    @Override
    public List<Path> listInputFiles(Path inputDir) {
        try (Stream<Path> stream = Files.list(inputDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(isInputFileValid())
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new FileNotOpenedException(CAN_T_LIST_FILES_IN_THE_DIRECTORY + inputDir, e);
        }
    }


    @Override
    public List<Operation> read(String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
        try {
            return objectMapper.readValue(file, OPERATION_LIST_TYPE);
        } catch (Exception e) {
            throw new FileNotOpenedException(FILE_NOT_OPENED_ERROR_MESSAGE + fileName, e);
        }
    }

    @Override
    public List<OperationDataResult> filterOperationsByMonth(List<Path> inputFiles) {
        return inputFiles.stream()
                .flatMap(this::getOperationsByMonth)
                .sorted(operationDateTimeComparator())
                .toList();
    }

    @Override
    public boolean shouldWriteHeader(Path outputCsv) {
        try {
            return !Files.exists(outputCsv) || Files.size(outputCsv) == 0;
        } catch (IOException e) {
            throw new RuntimeException(FILE_NOT_OPENED_ERROR_MESSAGE + outputCsv, e);
        }
    }

    @Override
    public void write(String toFileName, List<OperationDataResult> results, String csvHeader, boolean writeHeader) {
        String csvFromData = buildCsv(results, csvHeader);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(toFileName))) {
            bufferedWriter.write(csvFromData);
        } catch (IOException e) {
            throw new FileNotWrittenException(FILE_NOT_WRITTEN_ERROR_MESSAGE + toFileName, e);
        }
    }

    private Predicate<Path> isInputFileValid() {
        return path -> INPUT_FILE_PATTERN.matcher(path.getFileName().toString()).matches();
    }

    private Stream<OperationDataResult> getOperationsByMonth(Path filePath) {
        int monthIdentifier = extractMonthFromFilename(filePath.getFileName().toString());
        List<Operation> operations = read(filePath.toString());
        return convert(operations, monthIdentifier).stream();
    }

    private Comparator<? super OperationDataResult> operationDateTimeComparator() {
        return Comparator.comparing(OperationDataResult::getDate);
    }

    private List<OperationDataResult> convert(List<Operation> operations, int monthIdentifier) {
        return operations.stream()
                .map(OperationDataResult::new)
                .filter(operationDataResult ->
                        operationDataResult.getDate().getMonthValue() == monthIdentifier)
                .toList();
    }
}
