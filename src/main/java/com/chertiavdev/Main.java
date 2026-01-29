package com.chertiavdev;

import com.chertiavdev.models.OperationDataResult;
import com.chertiavdev.service.OperationFileService;
import com.chertiavdev.service.impl.OperationFileServiceImpl;
import java.nio.file.Path;
import java.util.List;

public class Main {

    private static final String PLEASE_PROVIDE_A_SINGLE_ARGUMENT_WITH_A_FILENAME =
            "Please provide a single argument with a filename.";
    private static final String CSV_RESULT_FILE = "/result.csv";
    private static final String CSV_HEADER =
            "date,time,location,duration,type,salaryMonth,salaryYear,color";
    private static final int EXPECTED_ARG_COUNT = 2;
    public static final int SOURCE_FILE_INDEX = 0;
    public static final int OUTPUT_CSV_INDEX = 1;
    public static final String FILES_WRITTEN_SUCCESSFULLY = "Files written successfully.";

    public static void main(String[] args) {
        if (args.length != EXPECTED_ARG_COUNT) {
            throw new IllegalArgumentException(PLEASE_PROVIDE_A_SINGLE_ARGUMENT_WITH_A_FILENAME);
        }

        Path inputDir = Path.of(args[SOURCE_FILE_INDEX]);
        Path outputCvsFile = Path.of(args[OUTPUT_CSV_INDEX] + CSV_RESULT_FILE);

        OperationFileService operationFileService = new OperationFileServiceImpl();
        List<Path> inputFiles = operationFileService.listInputFiles(inputDir);

        List<OperationDataResult> operationDataResults = operationFileService
                .filterOperationsByMonth(inputFiles);

        boolean writeHeader = operationFileService.shouldWriteHeader(outputCvsFile);

        operationFileService
                .write(outputCvsFile.toString(), operationDataResults, CSV_HEADER, writeHeader);

        System.out.println(FILES_WRITTEN_SUCCESSFULLY);
    }
}