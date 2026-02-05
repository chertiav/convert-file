package com.chertiavdev.stgategy.filter;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class PlanInputFileFilter implements FilterHandler {
    private static final Pattern INPUT_FILE_PATTERN = Pattern.compile("\\d{4}-\\d{2}\\.json");

    @Override
    public boolean isValid(Path path) {
        return INPUT_FILE_PATTERN.matcher(path.getFileName().toString()).matches();
    }
}
