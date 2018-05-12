package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class Row {
    private long ms;
    private String format;
    private String timeZoneId;

    public Row(@NotNull long ms, @NotNull String format, @NotNull String timeZoneId) {
        this.ms = ms;
        this.format = format;
        this.timeZoneId = timeZoneId;
    }

    @NotNull
    public ZonedDateTime getDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.of(timeZoneId));
    }

    @NotNull
    public String getText() {
        return DateTimeFormatter.ofPattern(format).format(getDateTime());
    }

    public long getMs() {
        return ms;
    }

    @NotNull
    public String getFormat() {
        return format;
    }

    @NotNull
    public String getTimeZoneId() {
        return timeZoneId;
    }
}
