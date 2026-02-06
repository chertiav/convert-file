package com.chertiavdev.dto.result;

import com.chertiavdev.dto.operation.OperationDto;
import com.chertiavdev.dto.operation.fact.FactOperationDto;
import com.chertiavdev.dto.operation.plan.PlanOperationDto;

public final class OperationDataResultFactory {
    private OperationDataResultFactory() {
    }

    public static OperationDataResult from(OperationDto dto) {
        if (dto instanceof PlanOperationDto plan) {
            return new OperationDataResult(plan);
        }

        if (dto instanceof FactOperationDto fact) {
            return new OperationDataResult(fact);
        }

        throw new IllegalArgumentException("Unsupported OperationDto type: " + dto.getClass());
    }
}
