package com.chertiavdev.enums;

public enum Mode {
    PLAN("--plan"),
    FACT("--fact");

    private final String flag;

    Mode(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
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
