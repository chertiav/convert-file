package com.chertiavdev.enums;

import lombok.Getter;

@Getter
public enum Mode {
    PLAN("--plan"),
    FACT("--fact");

    private final String flag;

    Mode(String flag) {
        this.flag = flag;
    }

    public static Mode fromArg(String type) {
        for (Mode mode : Mode.values()) {
            if (mode.getFlag().equals(type)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Invalid Mode type: " + type);
    }

}
