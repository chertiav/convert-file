package com.chertiavdev.stgategy;

import com.chertiavdev.enums.Mode;
import com.chertiavdev.stgategy.filter.FilterHandler;

public interface InputFileFilterStrategy {
    FilterHandler get(Mode mode);
}
