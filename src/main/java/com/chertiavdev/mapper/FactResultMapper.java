package com.chertiavdev.mapper;

import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.dto.result.fact.FactResultDto;
import com.chertiavdev.util.ServiceUtils;
import java.time.Duration;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FactResultMapper {
    FactResultMapper INSTANCE = Mappers.getMapper(FactResultMapper.class);

    @Mapping(target = "time", expression = "java(asString(factOperationDto.getTime()))")
    @Mapping(target = "duration", expression =
            "java(formatDurationSafe(factOperationDto.getDuration()))")
    @Mapping(target = "salaryMonth", ignore = true)
    @Mapping(target = "salaryYear", ignore = true)
    FactResultDto toDto(FactOperationDto factOperationDto);

    @AfterMapping
    default void updateSalaryInfo(FactOperationDto source, @MappingTarget FactResultDto target) {
        if (source.getDate() != null) {
            target.setSalaryMonth(source.getDate().getMonthValue());
            target.setSalaryYear(source.getDate().getYear());
        }
    }

    default String asString(Object time) {
        return time == null ? null : time.toString();
    }

    default String formatDurationSafe(Duration duration) {
        return ServiceUtils.formatDuration(duration);
    }
}
