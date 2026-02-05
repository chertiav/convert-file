package com.chertiavdev.dto.operation.fact;

import com.chertiavdev.dto.operation.OperationDto;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FactOperationDto extends OperationDto {
    private String type;
    private String rate;
    private String location;
    private String shiftType;
    private LocalDate date;
    private LocalTime time;
    private Duration duration;
    private String name;
    private String comment;
    private Double paidTimeAmount; // PT - base payment
    private Double eveningAllowance; // AT - evening allowance
    private Double nightAllowance; // NT – night bonus
    private Double sundayHolidayAllowance; // SH – Sunday / holiday
    private Double serviceAllowance; // SRV – service / special surcharge
    private Double extra;
}
