package com.chertiavdev;

import static com.chertiavdev.factory.AppFactory.fileConverterService;
import static com.chertiavdev.factory.AppFactory.fileReaderService;
import static com.chertiavdev.factory.AppFactory.inputFileListingService;

import com.chertiavdev.dto.operation.result.OperationDataResult;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileConverterService;
import com.chertiavdev.service.FileReaderService;
import com.chertiavdev.service.FileWriterService;
import com.chertiavdev.service.InputFileListingService;
import com.chertiavdev.service.impl.FileWriterServiceImpl;
import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final String USAGE = """
            Usage:
              convert-file --plan <inputDir> <outputDir>   // .json input files
              convert-file --fact <inputDir> <outputDir>   // .txt input files
              convert-file --help                          // show help
            """;
    private static final String USAGE_CONVERT_FILE_PLAN_FACT_INPUT_DIR_OUTPUT_DIR =
            "Usage: convert-file --plan|--fact <inputDir> <outputDir>";
    private static final String CSV_RESULT_FILE = "/result-%s.csv";
    private static final int EXPECTED_ARG_COUNT = 3;
    private static final int FIRST_ARG_INDEX = 0;
    private static final int INPUT_DIR_INDEX = 1;
    private static final int OUTPUT_DIR_INDEX = 2;
    private static final String FILES_WRITTEN_SUCCESSFULLY = "Files written successfully.";

    public static void main(String[] args) {
        if (args.length == 1 && "--help".equals(args[FIRST_ARG_INDEX])) {
            System.out.println(USAGE);
            return;
        }

        if (args.length != EXPECTED_ARG_COUNT) {
            throw new IllegalArgumentException(USAGE_CONVERT_FILE_PLAN_FACT_INPUT_DIR_OUTPUT_DIR);
        }

        Mode mode = Mode.fromArg(args[FIRST_ARG_INDEX]);
        Path inputDir = Path.of(args[INPUT_DIR_INDEX]);
        String outputCsvPath = args[OUTPUT_DIR_INDEX]
                + String.format(CSV_RESULT_FILE, mode.name().toLowerCase());

        InputFileListingService listingService = inputFileListingService();
        List<Path> inputFiles = listingService.getFilePaths(mode, inputDir);
        System.out.println(inputFiles);

        FileReaderService fileReaderService = fileReaderService();
        FileConverterService dataConverter = fileConverterService(fileReaderService);

        List<OperationDataResult> operationDataResults = dataConverter
                .convertAllFiles(mode, inputFiles);

        FileWriterService fileService = new FileWriterServiceImpl(outputCsvPath);
        fileService.write(operationDataResults);

        System.out.println(FILES_WRITTEN_SUCCESSFULLY);
    }
}
