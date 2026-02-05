package com.chertiavdev.stgategy;

import com.chertiavdev.enums.Mode;
import com.chertiavdev.stgategy.filter.FilterHandler;
import java.util.Map;

public class InputFileFilterStrategyImpl implements InputFileFilterStrategy {
    public static final String NO_INPUT_FILE_FILTER_CONFIGURED_FOR_MODE =
            "No input file filter configured for mode: ";
    private final Map<Mode, FilterHandler> filterHandlerMap;

    public InputFileFilterStrategyImpl(Map<Mode, FilterHandler> filterHandlerMap) {
        this.filterHandlerMap = filterHandlerMap;
    }

    @Override
    public FilterHandler get(Mode mode) {
        FilterHandler handler = filterHandlerMap.get(mode);
        if (handler == null) {
            throw new IllegalArgumentException(NO_INPUT_FILE_FILTER_CONFIGURED_FOR_MODE + mode);
        }
        return handler;
    }
}
