package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public enum DateTimePattern {

    MILLIS_PRECISION("dd/MM/yyyy - HH:mm:ss.SSS"),
    SECOND_PRECISION("dd/MM/yyyy - HH:mm:ss"),
    MINUTE_PRECISION("dd/MM/yyyy - HH:mm"),
    HOUR_PRECISION("dd/MM/yyyy - HH"),
    FULL_ZONE_OFFSET("dd/MM/yyyy'T'HH:mm:ss.SSSZ");

    private final String pattern;

    DateTimePattern(@NotNull String pattern) {
        this.pattern = pattern;
    }

    @NotNull
    public String getPattern() {
        return pattern;
    }

    @NotNull
    public static String[] getPatterns() {
        return Arrays.stream(DateTimePattern.values()).map(DateTimePattern::getPattern).toArray(String[]::new);
    }
}
