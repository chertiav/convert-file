package com.chertiavdev.dto.operation.plan;

import com.chertiavdev.dto.operation.OperationDto;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class PlanOperationDto extends OperationDto {
    private Integer id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;
    private String url;
    private String color;
    private String textColor;
    private Float overlayOpacity;
}
