package com.chertiavdev.mapper;

import static com.chertiavdev.util.ServiceUtils.formatDuration;

import com.chertiavdev.dto.operation.plan.PlanOperationDto;
import com.chertiavdev.dto.result.plan.PlanResultDto;
import com.chertiavdev.enums.OperationType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {OperationType.class})
public interface PlanResultMapper {
    PlanResultMapper INSTANCE = Mappers.getMapper(PlanResultMapper.class);
    int SALARY_MONTH_THRESHOLD = 12;
    int SALARY_PAYDAY_DECEMBER = 18;
    int DEFAULT_SALARY_PAYDAY = 19;
    int DAYS_ADDED_TO_GET_NEXT_MONTH = 25;
    ZoneId ZONE_ID = ZoneId.of("Europe/Copenhagen");

    @Mapping(target = "date", expression = "java(convertToLocalDate(planOperationDto.getStart()))")
    @Mapping(target = "time", expression = "java(titlePart(planOperationDto.getTitle(), 0))")
    @Mapping(target = "location", expression = "java(titlePart(planOperationDto.getTitle(), 1))")
    @Mapping(target = "duration", expression =
            "java(getDuration(planOperationDto.getStart(), planOperationDto.getEnd()))")
    @Mapping(target = "type",
            expression = "java(OperationType.fromColor(planOperationDto.getColor()).getType())")
    @Mapping(target = "salaryMonth", ignore = true)
    @Mapping(target = "salaryYear", ignore = true)
    @Mapping(target = "color", source = "color")
    PlanResultDto toDto(PlanOperationDto planOperationDto);

    @AfterMapping
    default void updateSalaryInfo(
            PlanOperationDto source,
            @MappingTarget PlanResultDto target
    ) {
        LocalDate date = target.getDate();
        target.setSalaryMonth(getSalaryMonth(date));
        target.setSalaryYear(getSalaryYear(date));
    }

    default LocalDate convertToLocalDate(LocalDateTime dateTime) {
        return dateTime.atZone(ZONE_ID).toLocalDate();
    }

    default String titlePart(String title, int index) {
        if (title == null) {
            return null;
        }
        String[] parts = title.split("\\R");
        return parts.length > index ? parts[index] : null;
    }

    default String getDuration(LocalDateTime start, LocalDateTime end) {
        return formatDuration(Duration.between(start, end));
    }

    default int getSalaryMonth(LocalDate date) {
        int monthValue = date.getMonthValue();
        int thresholdDay = monthValue == SALARY_MONTH_THRESHOLD
                ? SALARY_PAYDAY_DECEMBER
                : DEFAULT_SALARY_PAYDAY;

        return date.getDayOfMonth() <= thresholdDay
                ? monthValue
                : (date.plusDays(DAYS_ADDED_TO_GET_NEXT_MONTH)).getMonthValue();
    }

    default int getSalaryYear(LocalDate date) {
        int monthValue = date.getMonthValue();
        int thresholdDay = monthValue == SALARY_MONTH_THRESHOLD
                ? SALARY_PAYDAY_DECEMBER
                : DEFAULT_SALARY_PAYDAY;

        return date.getDayOfMonth() <= thresholdDay
                ? date.getYear()
                : (date.plusDays(DAYS_ADDED_TO_GET_NEXT_MONTH)).getYear();
    }
}
