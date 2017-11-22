package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static ZonedDateTime nowGasDay() {
        final ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        now.withHour(6);
        now.withMinute(0);
        now.withSecond(0);
        now.withNano(0);

        return now;
    }

    public static ZonedDateTime zeroDay() {
        return Instant.ofEpochMilli(0).atZone(ZoneOffset.UTC);
    }

    public static boolean isLong(String text) {
        try {
            Long.parseLong(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDateTime(String text, String format) {
        try {
            ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(ZoneOffset.UTC));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isFormat(String text) {
        return text != null && !text.trim().isEmpty();
    }

    public static String toText(@NotNull ZonedDateTime dateTime, @NotNull String format) {
        return DateTimeFormatter.ofPattern(format).format(dateTime);
    }

    public static long toMs(@NotNull ZonedDateTime dateTime) {
        return dateTime.toInstant().toEpochMilli();
    }

    public static ZonedDateTime toDateTime(@NotNull String text) {
        return Instant.ofEpochMilli(Long.valueOf(text)).atZone(ZoneOffset.UTC);
    }

    public static ZonedDateTime toDateTime(@NotNull String text, @NotNull String format) {
        return ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(ZoneOffset.UTC));
    }
}
