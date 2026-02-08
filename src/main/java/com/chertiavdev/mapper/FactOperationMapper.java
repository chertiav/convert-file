package com.chertiavdev.mapper;

import static com.chertiavdev.util.ServiceUtils.parseDuration;

import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.dto.operation.fact.FactOperationRawDto;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(imports = {LocalDate.class, LocalTime.class})
public interface FactOperationMapper {

    FactOperationMapper INSTANCE = Mappers.getMapper(FactOperationMapper.class);
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM-uuuu");
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    Pattern DURATION_PATTERN = Pattern.compile(
            "(?:(\\d+)\\s*[ht])?\\s*(?:(\\d+)\\s*m)?",
            Pattern.CASE_INSENSITIVE
    );
    String EXTRA_DURATION_PREFIX = "(Extra:";
    String EXTRA_DURATION_PATTERN = "\\(Extra:\\s*([^)]*)\\)";

    @Mapping(target = "type", source = "type")
    @Mapping(target = "rate", source = "rate")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "shiftType", source = "shiftType")
    @Mapping(target = "date", expression = "java(LocalDate.parse(raw.date(), DATE_FORMATTER))")
    @Mapping(target = "time", expression = "java(LocalTime.parse(raw.time(), TIME_FORMATTER))")
    @Mapping(target = "duration", expression = "java(calculateDuration(raw.durationRaw()))")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "comment",
            expression = "java(buildComment(raw.comment(), raw.durationRaw()))"
    )
    @Mapping(target = "paidTimeAmount", expression =
            "java(com.chertiavdev.util.ServiceUtils.parseDecimal(raw.paidTimeAmount()))")
    @Mapping(target = "eveningAllowance", expression =
            "java(com.chertiavdev.util.ServiceUtils.parseDecimal(raw.eveningAllowance()))")
    @Mapping(target = "nightAllowance", expression =
            "java(com.chertiavdev.util.ServiceUtils.parseDecimal(raw.nightAllowance()))")
    @Mapping(target = "sundayHolidayAllowance", expression =
            "java(com.chertiavdev.util.ServiceUtils.parseDecimal(raw.sundayHolidayAllowance()))"
    )
    @Mapping(target = "serviceAllowance", expression =
            "java(com.chertiavdev.util.ServiceUtils.parseDecimal(raw.serviceAllowance()))")
    @Mapping(target = "extra", expression =
            "java(com.chertiavdev.util.ServiceUtils.parseDecimal(raw.extra()))"
    )
    FactOperationDto toFactOperationDto(FactOperationRawDto raw);

    default Duration calculateDuration(String durationRaw) {
        ExtraDuration extraDuration = extractExtraDuration(durationRaw);
        Duration base = parseDuration(extraDuration.baseDuration(), DURATION_PATTERN);
        Duration extra = parseDuration(extraDuration.extraDuration(), DURATION_PATTERN);
        return base.plus(extra);
    }

    default String buildComment(String baseCommentRaw, String durationRaw) {
        String baseComment = baseCommentRaw != null ? baseCommentRaw.trim() : "";
        String extraComment = extractExtraDuration(durationRaw).commentSuffix();
        extraComment = extraComment != null ? extraComment.trim() : "";
        return mergeComments(baseComment, extraComment);
    }

    default String mergeComments(String baseComment, String extraComment) {
        if (extraComment.isEmpty()) {
            return baseComment;
        }
        return baseComment.isEmpty() ? extraComment : baseComment + " " + extraComment;
    }

    default ExtraDuration extractExtraDuration(String raw) {
        if (raw == null) {
            return new ExtraDuration("", "", "");
        }

        String base = raw;
        String extra = "";
        String comment = "";

        int extraIndex = raw.indexOf(EXTRA_DURATION_PREFIX);
        if (extraIndex >= 0) {
            base = raw.substring(0, extraIndex).trim();
            comment = raw.substring(extraIndex).trim();

            Matcher m = Pattern.compile(EXTRA_DURATION_PATTERN, Pattern.CASE_INSENSITIVE)
                    .matcher(comment);
            if (m.find()) {
                extra = m.group(1).trim();
            }
        }

        return new ExtraDuration(base, extra, comment);
    }

    record ExtraDuration(String baseDuration, String extraDuration, String commentSuffix) {
    }
}
