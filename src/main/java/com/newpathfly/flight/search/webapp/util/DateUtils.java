package com.newpathfly.flight.search.webapp.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateUtils {

    public static final DateTimeFormatter INPUT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("uuuuMMdd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    public static final DateTimeFormatter OUTPUT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private DateUtils() {
    }

    public static LocalDateTime getLocalDateTime(String date, String time) {
        return LocalDateTime.parse(date + " " + time, INPUT_DATETIME_FORMATTER);
    }
}
