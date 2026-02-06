package com.chertiavdev.strategy;

import com.chertiavdev.enums.Mode;
import com.chertiavdev.strategy.converter.FileConverterHandler;

public interface FileConverterStrategy {
    FileConverterHandler get(Mode mode);
}
