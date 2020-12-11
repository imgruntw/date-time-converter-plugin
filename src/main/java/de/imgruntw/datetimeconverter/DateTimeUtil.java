package de.imgruntw.datetimeconverter;

import com.intellij.openapi.application.ex.ClipboardUtil;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public final class DateTimeUtil {

    private DateTimeUtil() {
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
            ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault()));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isFormat(String text) {
        try {
            DateTimeFormatter.ofPattern(text);
            return !text.trim().isEmpty();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isTimeZoneId(String text) {
        if (text != null && !text.isEmpty()) {
            final String gmt = "GMT";
            return text.equals(gmt) || !TimeZone.getTimeZone(text).getID().equals(gmt);
        } else {
            return false;
        }
    }

    @NotNull
    public static List<Long> getClipboardDateTimes() {
        final List<Long> dateTimes = new ArrayList<>();
        final String text = ClipboardUtil.getTextInClipboard();

        if (text != null) {
            final String[] lines = splitMultipleLines(text);

            for (String line : lines) {
                if (DateTimeUtil.isLong(line)) {
                    dateTimes.add(Long.parseLong(line));
                }
            }
        }

        return dateTimes;
    }

    private static String[] splitMultipleLines(String text) {
        final String[] lines;

        if (text.contains("\r\n")) {
            lines = text.split("\r\n");
        } else if (text.contains("\n")) {
            lines = text.split("\n");
        } else {
            lines = text.split(System.lineSeparator());
        }

        return lines;
    }

    public static long toMs(String text, String format, String currentTimeZoneId) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of(currentTimeZoneId));
        final ZonedDateTime zonedDateTime = ZonedDateTime.parse(text, formatter);

        return zonedDateTime.toInstant().toEpochMilli();
    }

    public static long getCurrentGasDayMs() {
        final ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault())
                .withHour(6)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        return now.toInstant().toEpochMilli();
    }
}
