package com.chertiavdev.strategy;

import com.chertiavdev.enums.Mode;
import com.chertiavdev.strategy.filter.FilterHandler;

public interface InputFileFilterStrategy {
    FilterHandler get(Mode mode);
}
