package com.chertiavdev.service.impl;

import com.chertiavdev.domain.Mode;
import com.chertiavdev.dto.result.OperationDataResult;
import com.chertiavdev.service.FileWriterService;
import com.chertiavdev.strategy.WriteFileStrategy;
import com.chertiavdev.strategy.writer.WriteFileHandler;
import java.util.List;

public class FileWriterServiceImpl implements FileWriterService {
    private final WriteFileStrategy writeFileStrategy;
    private final String filePath;

    public FileWriterServiceImpl(WriteFileStrategy writeFileStrategy, String filePath) {
        this.writeFileStrategy = writeFileStrategy;
        this.filePath = filePath;
    }

    @Override
    public <T extends OperationDataResult> void write(Mode mode, List<T> data) {
        WriteFileHandler<T> writeFileHandler = writeFileStrategy.get(mode);
        writeFileHandler.write(data, filePath);
    }
}
