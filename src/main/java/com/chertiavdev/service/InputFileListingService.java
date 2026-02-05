package com.chertiavdev.service;

import com.chertiavdev.enums.Mode;
import java.nio.file.Path;
import java.util.List;

public interface InputFileListingService {
    List<Path> getFilePaths(Mode mode, Path inputDir);
}
