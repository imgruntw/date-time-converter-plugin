package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;

public final class Row {
    private ZonedDateTime dateTime;
    private String format;

    public Row(@NotNull ZonedDateTime dateTime, @NotNull String format) {
        this.dateTime = dateTime;
        this.format = format;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
