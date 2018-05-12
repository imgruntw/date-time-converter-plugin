package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Collectors;

public enum TimeZoneId {
    UTC("UTC"),
    LONDON("Europe/London"),
    BERLIN("Europe/Berlin"),
    MOSCOW("Europe/Moscow"),
    NEW_YORK("America/New_York"),
    LOS_ANGELES("America/Los_Angeles");

    private String id;

    TimeZoneId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public static String getDefaultId() {
        return ZoneId.systemDefault().getId();
    }

    @NotNull
    public static String[] getIds() {
        final TreeSet<String> ids = Arrays.stream(TimeZoneId.values()).map(TimeZoneId::getId).collect(Collectors.toCollection(TreeSet::new));
        ids.add(getDefaultId());

        return ids.toArray(new String[ids.size()]);
    }
}
