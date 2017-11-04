package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class DateTimeTableModel extends AbstractTableModel {

    private static final String FORMAT = "dd/MM/yyyy - HH:mm:ss.SSS";

    private final List<ZonedDateTime> rows;

    public DateTimeTableModel() {
        this.rows = new ArrayList<>();
    }

    public void addRow(@NotNull ZonedDateTime dateTime) {
        rows.add(dateTime);
        fireTableDataChanged();
    }

    public void deleteRow(int index) {
        rows.remove(index);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final ZonedDateTime row = rows.get(rowIndex);

        if (columnIndex == 0) {
            return row.toInstant().toEpochMilli();
        } else {
            return DateTimeFormatter.ofPattern(FORMAT).format(row);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        final String text = (String) aValue;
        final ZonedDateTime row;

        if (columnIndex == 0) {
            row = Instant.ofEpochMilli(Long.valueOf(text)).atZone(ZoneOffset.UTC);
        } else {
            row = ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(FORMAT).withZone(ZoneOffset.UTC));
        }

        rows.add(rowIndex, row);
    }
}
