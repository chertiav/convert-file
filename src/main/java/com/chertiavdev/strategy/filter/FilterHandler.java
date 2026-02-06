package com.chertiavdev.strategy.filter;

import java.nio.file.Path;

public interface FilterHandler {
    boolean isValid(Path path);
}
