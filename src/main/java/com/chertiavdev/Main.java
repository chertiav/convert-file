package com.chertiavdev;

import static com.chertiavdev.util.ServiceUtils.extractMonthFromFilename;

import com.chertiavdev.models.Operation;
import com.chertiavdev.models.OperationDataResult;
import com.chertiavdev.service.OperationFileService;
import com.chertiavdev.service.impl.OperationFileServiceImpl;
import java.util.List;

public class Main {

    private static final String PLEASE_PROVIDE_A_SINGLE_ARGUMENT_WITH_A_FILENAME =
            "Please provide a single argument with a filename.";
    private static final String CSV_EXTENSION = ".csv";
    private static final String CSV_HEADER =
            "date,time,location,duration,type,salaryMonth,salaryYear";

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException(PLEASE_PROVIDE_A_SINGLE_ARGUMENT_WITH_A_FILENAME);
        }

        String fromFileName = args[0];
        String toFileName = fromFileName.substring(0, fromFileName.indexOf(".")) + CSV_EXTENSION;
        int monthIdentifier = extractMonthFromFilename(fromFileName);

        OperationFileService operationFileService = new OperationFileServiceImpl();
        List<Operation> operations = operationFileService.read(fromFileName);
        List<OperationDataResult> operationDataResults = operationFileService
                .convert(operations, monthIdentifier);
        operationFileService.write(toFileName, operationDataResults, CSV_HEADER);
        System.out.println("File written successfully.");
    }
}