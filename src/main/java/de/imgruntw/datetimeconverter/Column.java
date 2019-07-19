package de.imgruntw.datetimeconverter;

import java.util.Optional;

public enum Column {

    VALUE(0),
    DATE_TIME(1),
    FORMAT(2),
    TIME_ZONE(3);

    private final int index;

    Column(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static Optional<Column> of(int index) {
        for (Column column : Column.values()) {
            if (column.index == index) {
                return Optional.of(column);
            }
        }

        return Optional.empty();
    }
}
