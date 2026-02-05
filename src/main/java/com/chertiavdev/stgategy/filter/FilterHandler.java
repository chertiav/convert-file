package com.chertiavdev.stgategy.filter;

import java.nio.file.Path;

public interface FilterHandler {
    boolean isValid(Path path);
}
