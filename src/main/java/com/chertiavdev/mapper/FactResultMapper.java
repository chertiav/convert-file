package com.chertiavdev.mapper;

import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.dto.result.fact.FactResultDto;
import com.chertiavdev.util.AppHelper;
import java.time.Duration;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FactResultMapper {
    FactResultMapper INSTANCE = Mappers.getMapper(FactResultMapper.class);

    @Mapping(target = "time", expression = "java(asString(dto.getTime()))")
    @Mapping(target = "duration", expression =
            "java(formatDurationSafe(dto.getDuration()))")
    @Mapping(target = "salaryMonth", ignore = true)
    @Mapping(target = "salaryYear", ignore = true)
    @Mapping(target = "paidTimeAmount", expression =
            "java(com.chertiavdev.util.AppHelper.formatDecimal(dto.getPaidTimeAmount()))")
    @Mapping(target = "eveningAllowance", expression =
            "java(com.chertiavdev.util.AppHelper.formatDecimal(dto.getEveningAllowance()))")
    @Mapping(target = "nightAllowance", expression =
            "java(com.chertiavdev.util.AppHelper.formatDecimal(dto.getNightAllowance()))")
    @Mapping(target = "sundayHolidayAllowance", expression =
            "java(com.chertiavdev.util.AppHelper.formatDecimal(dto.getSundayHolidayAllowance()))"
    )
    @Mapping(target = "serviceAllowance", expression =
            "java(com.chertiavdev.util.AppHelper.formatDecimal(dto.getServiceAllowance()))")
    @Mapping(target = "extra", expression =
            "java(com.chertiavdev.util.AppHelper.formatDecimal(dto.getExtra()))"
    )
    FactResultDto toDto(FactOperationDto dto);

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
        return AppHelper.formatDuration(duration);
    }
}
