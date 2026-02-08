package com.chertiavdev.domain;

import java.util.Set;
import lombok.Getter;

@Getter
public enum OperationType {
    SUBSCRIPTION_CLEANING(
            "Abonnementsrengøring",
            Set.of("#080480")
    ),
    PERIODIC_TASKS(
            "Periodiske opgaver",
            Set.of("#AFC713", "#82B0FF", "#AFC71388")
    ),
    UNKNOWN(
            "UNKNOWN TYPE",
            Set.of()
    );

    private final String type;
    private final Set<String> colors;

    OperationType(String type, Set<String> colors) {
        this.type = type;
        this.colors = colors;
    }

    public static OperationType fromColor(String color) {
        if (color == null) {
            return UNKNOWN;
        }

        for (OperationType type : values()) {
            if (type.colors.contains(color)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
