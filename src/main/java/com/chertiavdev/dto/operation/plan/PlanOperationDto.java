package com.chertiavdev.dto.operation.plan;

import com.chertiavdev.dto.operation.OperationDto;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlanOperationDto extends OperationDto {
    private Integer id;
    private String title;
    private Date start;
    private Date end;
    private String url;
    private String color;
    private String textColor;
    private Float overlayOpacity;
}
