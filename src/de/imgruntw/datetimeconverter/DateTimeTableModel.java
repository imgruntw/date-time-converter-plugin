package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public final class DateTimeTableModel extends AbstractTableModel {

    private static final String DEFAULT_FORMAT = "dd/MM/yyyy - HH:mm:ss.SSS";
    private static final int MS_COLUMN = 0;
    private static final int DATE_TIME_COLUMN = 1;
    private static final int FORMAT_COLUMN = 2;

    private final List<Row> rows;

    public DateTimeTableModel() {
        this.rows = new ArrayList<>();
    }

    public void addRow(@NotNull ZonedDateTime dateTime) {
        rows.add(new Row(dateTime, DEFAULT_FORMAT));
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
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Row row = rows.get(rowIndex);

        switch (columnIndex) {
            case MS_COLUMN:
                return DateTimeUtil.toMs(row.getDateTime());
            case DATE_TIME_COLUMN:
                return DateTimeUtil.toText(row.getDateTime(), row.getFormat());
            case FORMAT_COLUMN:
                return row.getFormat();
            default:
                throw new IllegalArgumentException("unknown column index: " + columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        final String text = (String) aValue;
        final ZonedDateTime currentDateTime = rows.get(rowIndex).getDateTime();
        final String currentFormat = rows.get(rowIndex).getFormat();
        final Row row;

        switch (columnIndex) {
            case MS_COLUMN:
                if (DateTimeUtil.isLong(text)) {
                    row = new Row(DateTimeUtil.toDateTime(text), currentFormat);
                } else {
                    row = new Row(DateTimeUtil.zeroDay(), currentFormat);
                }
                break;
            case DATE_TIME_COLUMN:
                if (DateTimeUtil.isDateTime(text, currentFormat)) {
                    row = new Row(DateTimeUtil.toDateTime(text, currentFormat), currentFormat);
                } else {
                    row = new Row(DateTimeUtil.zeroDay(), currentFormat);
                }
                break;
            case FORMAT_COLUMN:
                if (DateTimeUtil.isFormat(text)) {
                    row = new Row(currentDateTime, text);
                } else {
                    row = new Row(DateTimeUtil.zeroDay(), DEFAULT_FORMAT);
                }
                break;
            default:
                throw new IllegalArgumentException("unknown column index: " + columnIndex);
        }

        rows.set(rowIndex, row);
    }
}
