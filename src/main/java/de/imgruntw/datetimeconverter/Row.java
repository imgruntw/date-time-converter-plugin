package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class Row {

    private static final String DEFAULT_FORMAT = DateTimePattern.MILLIS_PRECISION.getPattern();
    private static final String DEFAULT_TIME_ZONE_ID = TimeZoneId.getDefaultId();

    private static final int MULTIPLICATOR_SECONDS = 1000;
    private static final int MULTIPLICATOR_MINUTES = 60 * 1000;
    private static final int MULTIPLICATOR_HOURS = 60 * 60 * 1000;

    private final long ms;
    private final String format;
    private final String timeZoneId;
    private final ChronoUnit unit;

    private Row(long ms, String format, String timeZoneId, ChronoUnit unit) {
        this.ms = ms;
        this.format = format;
        this.timeZoneId = timeZoneId;
        this.unit = unit;
    }

    public long getMs() {
        return ms;
    }

    public long getRepresentationValue() {
        switch (unit) {
            case MILLIS:
                return ms;
            case SECONDS:
                return ms / MULTIPLICATOR_SECONDS;
            case MINUTES:
                return ms / MULTIPLICATOR_MINUTES;
            case HOURS:
                return ms / MULTIPLICATOR_HOURS;
            default:
                throw new IllegalArgumentException("unknown date time unit: " + unit);
        }
    }

    @NotNull
    public ZonedDateTime getDateTime() {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.of(timeZoneId));
    }

    @NotNull
    public String getText() {
        return DateTimeFormatter.ofPattern(format).format(getDateTime());
    }

    @NotNull
    public String getFormat() {
        return format;
    }

    @NotNull
    public String getTimeZoneId() {
        return timeZoneId;
    }

    public static Row ofCopy(@NotNull Row row, @NotNull ChronoUnit unit) {
        return new Row(row.getMs(), row.getFormat(), row.getTimeZoneId(), unit);
    }

    public static Row ofDefault(@NotNull ChronoUnit unit, long ms) {
        return new Row(ms, DEFAULT_FORMAT, DEFAULT_TIME_ZONE_ID, unit);
    }

    public static Row ofRepresentationValue(@NotNull Row row, @NotNull ChronoUnit unit, long representationValue) {
        return new Row(toMs(representationValue, unit), row.getFormat(), row.getTimeZoneId(), unit);
    }

    public static Row ofMs(@NotNull Row row, @NotNull ChronoUnit unit, long ms) {
        return new Row(ms, row.getFormat(), row.getTimeZoneId(), unit);
    }

    public static Row ofFormat(@NotNull Row row, @NotNull ChronoUnit unit, @NotNull String format) {
        return new Row(row.getMs(), format, row.getTimeZoneId(), unit);
    }

    public static Row ofTimeZoneId(@NotNull Row row, @NotNull ChronoUnit unit, @NotNull String timeZoneId) {
        return new Row(row.getMs(), row.getFormat(), timeZoneId, unit);
    }

    private static long toMs(long representationValue, ChronoUnit unit) {
        switch (unit) {
            case MILLIS:
                return representationValue;
            case SECONDS:
                return representationValue * MULTIPLICATOR_SECONDS;
            case MINUTES:
                return representationValue * MULTIPLICATOR_MINUTES;
            case HOURS:
                return representationValue * MULTIPLICATOR_HOURS;
            default:
                throw new IllegalArgumentException("unknown date time unit: " + unit);
        }
    }
}
