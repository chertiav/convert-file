package com.chertiavdev.dto.result.plan;

import com.chertiavdev.export.csv.AbstractCsvWritable;
import com.chertiavdev.export.csv.CsvWritable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanResultDto extends AbstractCsvWritable implements CsvWritable {
    private LocalDate date;
    private String time;
    private String location;
    private String duration;
    private String type;
    private int salaryMonth;
    private int salaryYear;
    private String color;

    @Override
    public String toCsvLine() {
        return joinCsv(
                date,
                time,
                location,
                duration,
                type,
                salaryMonth,
                salaryYear,
                color
        );
    }
}
