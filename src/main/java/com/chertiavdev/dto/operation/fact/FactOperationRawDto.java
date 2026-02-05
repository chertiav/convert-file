package com.chertiavdev.dto.operation.fact;

public record FactOperationRawDto(
        String type,
        String rate,
        String location,
        String shiftType,
        String date,
        String time,
        String durationRaw,
        String name,
        String comment,
        String paidTimeAmount,
        String eveningAllowance,
        String nightAllowance,
        String sundayHolidayAllowance,
        String serviceAllowance,
        String extra
) {
}
