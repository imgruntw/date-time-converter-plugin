package de.imgruntw.datetimeconverter;

import com.intellij.openapi.application.ex.ClipboardUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

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
        try {
            DateTimeFormatter.ofPattern(text);
            return text != null && !text.trim().isEmpty();
        } catch (IllegalArgumentException e) {
            return false;
        }
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

    public static List<ZonedDateTime> getClipboardDateTimes() {
        final List<ZonedDateTime> dateTimes = new ArrayList<>();
        final String[] lines = ClipboardUtil.getTextInClipboard().split(System.lineSeparator());

        for (String line : lines) {
            if (DateTimeUtil.isLong(line)) {
                dateTimes.add(DateTimeUtil.toDateTime(line));
            }
        }

        return dateTimes;
    }
}
