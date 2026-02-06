package com.chertiavdev.factory;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.enums.Mode;
import com.chertiavdev.service.FileConverterService;
import com.chertiavdev.service.FileReaderService;
import com.chertiavdev.service.InputFileListingService;
import com.chertiavdev.service.impl.FileConverterServiceImpl;
import com.chertiavdev.service.impl.FileReaderServiceImpl;
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
import java.util.Map;

public final class AppFactory {
    private AppFactory() {
    }

    public static InputFileListingService inputFileListingService() {
        Map<Mode, FilterHandler> filterHandlerMap = Map.of(
                Mode.PLAN, new PlanInputFileFilter(),
                Mode.FACT, new FactInputFileFilter()
        );
        InputFileFilterStrategy inputFileFilterStrategy =
                new InputFileFilterStrategyImpl(filterHandlerMap);

        return new InputFileListingServiceImpl(inputFileFilterStrategy);
    }

    public static FileReaderService fileReaderService() {
        Map<Mode, ReadFileHandler<? extends OperationDto>> readFileHandlerMap = Map.of(
                Mode.PLAN, new PlanReadFile(),
                Mode.FACT, new FactReadFile()
        );

        ReadFileStrategy readFileStrategy = new ReadFileStrategyImpl(readFileHandlerMap);

        return new FileReaderServiceImpl(readFileStrategy);
    }

    public static FileConverterService fileConverterService(
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
