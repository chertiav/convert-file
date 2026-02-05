package com.chertiavdev.stgategy;

import com.chertiavdev.enums.Mode;
import com.chertiavdev.stgategy.converter.FileConverterHandler;

public interface FileConverterStrategy {
    FileConverterHandler get(Mode mode);
}
