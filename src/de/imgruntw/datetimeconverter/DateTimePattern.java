package de.imgruntw.datetimeconverter;

import java.util.Arrays;

public enum DateTimePattern {

    FULL("dd/MM/yyyy - HH:mm:ss.SSS"),
    SEOND_PRECISION("dd/MM/yyyy - HH:mm:ss"),
    MINUTE_PRECISION("dd/MM/yyyy - HH:mm"),
    HOUR_PRECISION("dd/MM/yyyy - HH"),
    FULL_ZONE_OFFSET("dd/MM/yyyy'T'HH:mm:ss.SSSZ");

    private String pattern;

    DateTimePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public static String[] getPatterns() {
        return Arrays.stream(DateTimePattern.values()).map(DateTimePattern::getPattern).toArray(String[]::new);
    }
}
