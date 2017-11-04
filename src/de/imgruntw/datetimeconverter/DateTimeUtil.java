package de.imgruntw.datetimeconverter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

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
}
