package com.chertiavdev.service.impl;

import com.chertiavdev.enums.Mode;
import com.chertiavdev.exceptions.FileNotOpenedException;
import com.chertiavdev.service.InputFileListingService;
import com.chertiavdev.stgategy.InputFileFilterStrategy;
import com.chertiavdev.stgategy.filter.FilterHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class InputFileListingServiceImpl implements InputFileListingService {
    private static final String CAN_T_LIST_FILES_IN_THE_DIRECTORY =
            "Can't list files in the directory: ";
    private final InputFileFilterStrategy inputFileFilterStrategy;

    public InputFileListingServiceImpl(InputFileFilterStrategy inputFileFilterStrategy) {
        this.inputFileFilterStrategy = inputFileFilterStrategy;
    }

    @Override
    public List<Path> getFilePaths(Mode mode, Path inputDir) {
        FilterHandler filterHandler = inputFileFilterStrategy.get(mode);
        try (Stream<Path> stream = Files.list(inputDir)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(filterHandler::isValid)
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new FileNotOpenedException(CAN_T_LIST_FILES_IN_THE_DIRECTORY + inputDir, e);
        }
    }
}
