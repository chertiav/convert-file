package com.chertiavdev;

import com.chertiavdev.dto.operation.OperationDataResult;
import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileConverterService;
import com.chertiavdev.service.FileReaderService;
import com.chertiavdev.service.FileWriterService;
import com.chertiavdev.service.InputFileListingService;
import com.chertiavdev.service.impl.FileConverterServiceImpl;
import com.chertiavdev.service.impl.FileReaderServiceImpl;
import com.chertiavdev.service.impl.FileWriterServiceImpl;
import com.chertiavdev.service.impl.InputFileListingServiceImpl;
import com.chertiavdev.strategy.FileConverterStrategy;
import com.chertiavdev.strategy.FileConverterStrategyImpl;
import com.chertiavdev.strategy.InputFileFilterStrategy;
import com.chertiavdev.strategy.InputFileFilterStrategyImpl;
import com.chertiavdev.strategy.ReadFileStrategy;
import com.chertiavdev.strategy.ReadFileStrategyImpl;
import com.chertiavdev.strategy.converter.FactFileConverter;
import com.chertiavdev.strategy.converter.FileConverterHandler;
import com.chertiavdev.strategy.converter.PlanFileConverter;
import com.chertiavdev.strategy.filter.FactInputFileFilter;
import com.chertiavdev.strategy.filter.FilterHandler;
import com.chertiavdev.strategy.filter.PlanInputFileFilter;
import com.chertiavdev.strategy.read.FactReadFile;
import com.chertiavdev.strategy.read.PlanReadFile;
import com.chertiavdev.strategy.read.ReadFileHandler;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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
    private static final int MODE_INDEX = 0;
    private static final int INPUT_DIR_INDEX = 1;
    private static final int OUTPUT_DIR_INDEX = 2;
    private static final String FILES_WRITTEN_SUCCESSFULLY = "Files written successfully.";

    public static void main(String[] args) {
        if (args.length == 1 && "--help".equals(args[0])) {
            System.out.println(USAGE);
            return;
        }

        if (args.length != EXPECTED_ARG_COUNT) {
            throw new IllegalArgumentException(USAGE_CONVERT_FILE_PLAN_FACT_INPUT_DIR_OUTPUT_DIR);
        }

        Mode mode = Mode.fromArg(args[MODE_INDEX]);
        Path inputDir = Path.of(args[INPUT_DIR_INDEX]);
        String outputCsvPath = args[OUTPUT_DIR_INDEX]
                + String.format(CSV_RESULT_FILE, mode.name().toLowerCase());

        InputFileListingService listingService = getInputFileListingService();
        List<Path> inputFiles = listingService.getFilePaths(mode, inputDir);
        System.out.println(inputFiles);

        FileReaderService fileReaderService = getFileReaderService();
        FileConverterService dataConverter = getDataConverterService(fileReaderService);

        List<OperationDataResult> operationDataResults = dataConverter
                .convertAllFiles(mode, inputFiles);

        FileWriterService fileService = new FileWriterServiceImpl(outputCsvPath);
        fileService.write(operationDataResults);

        System.out.println(FILES_WRITTEN_SUCCESSFULLY);
    }

    private static InputFileListingService getInputFileListingService() {
        Map<Mode, FilterHandler> filterHandlerMap = Map.of(
                Mode.PLAN, new PlanInputFileFilter(),
                Mode.FACT, new FactInputFileFilter()
        );
        InputFileFilterStrategy inputFileFilterStrategy =
                new InputFileFilterStrategyImpl(filterHandlerMap);
        return new InputFileListingServiceImpl(inputFileFilterStrategy);
    }

    private static FileReaderService getFileReaderService() {
        Map<Mode, ReadFileHandler<? extends OperationDto>> readFileHandlerMap = Map.of(
                Mode.PLAN, new PlanReadFile(),
                Mode.FACT, new FactReadFile()
        );

        ReadFileStrategy readFileStrategy = new ReadFileStrategyImpl(readFileHandlerMap);
        return new FileReaderServiceImpl(readFileStrategy);
    }

    private static FileConverterService getDataConverterService(
            FileReaderService fileReaderService
    ) {
        Map<Mode, FileConverterHandler> fileConverterHandlerMap = Map.of(
                Mode.PLAN, new PlanFileConverter(fileReaderService),
                Mode.FACT, new FactFileConverter(fileReaderService)
        );

        FileConverterStrategy fileConverterStrategy =
                new FileConverterStrategyImpl(fileConverterHandlerMap);
        return new FileConverterServiceImpl(fileConverterStrategy);
    }
}
