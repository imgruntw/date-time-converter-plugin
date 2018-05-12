package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DateTimeTableModel extends AbstractTableModel {

    public static final int MS_COLUMN = 0;
    public static final int DATE_TIME_COLUMN = 1;
    public static final int FORMAT_COLUMN = 2;
    public static final int TIME_ZONE_COLUMN = 3;

    private static final String DEFAULT_FORMAT = DateTimePattern.MILLIS_PRECISION.getPattern();
    private static final String DEFAULT_TIME_ZONE_ID = TimeZoneId.getDefaultId();

    private final List<Row> rows;

    public DateTimeTableModel() {
        this.rows = new ArrayList<>();
    }

    public void addRow(long ms) {
        rows.add(new Row(ms, DEFAULT_FORMAT, DEFAULT_TIME_ZONE_ID));
        fireTableDataChanged();
    }

    public void addRows(@NotNull Collection<Long> ms) {
        ms.forEach(i -> rows.add(new Row(i, DEFAULT_FORMAT, DEFAULT_TIME_ZONE_ID)));
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
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        final Row row = rows.get(rowIndex);

        switch (columnIndex) {
            case MS_COLUMN:
                return row.getMs();
            case DATE_TIME_COLUMN:
                return row.getText();
            case FORMAT_COLUMN:
                return row.getFormat();
            case TIME_ZONE_COLUMN:
                return row.getTimeZoneId();
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
        final Row currentRow = rows.get(rowIndex);
        final long currentMs = currentRow.getMs();
        final String currentFormat = currentRow.getFormat();
        final String currentTimeZoneId = currentRow.getTimeZoneId();
        final Row row;

        switch (columnIndex) {
            case MS_COLUMN:
                if (DateTimeUtil.isLong(text)) {
                    row = new Row(Long.parseLong(text), currentFormat, currentTimeZoneId);
                } else {
                    row = currentRow;
                }
                break;
            case DATE_TIME_COLUMN:
                if (DateTimeUtil.isDateTime(text, currentFormat)) {
                    row = new Row(DateTimeUtil.toMs(text, currentFormat, currentTimeZoneId), currentFormat, currentTimeZoneId);
                } else {
                    row = currentRow;
                }
                break;
            case FORMAT_COLUMN:
                if (DateTimeUtil.isFormat(text)) {
                    row = new Row(currentMs, text, currentTimeZoneId);
                } else {
                    row = currentRow;
                }
                break;
            case TIME_ZONE_COLUMN:
                if (DateTimeUtil.isTimeZoneId(text)) {
                    row = new Row(currentMs, currentFormat, text);
                } else {
                    row = currentRow;
                }
                break;
            default:
                throw new IllegalArgumentException("unknown column index: " + columnIndex);
        }

        rows.set(rowIndex, row);
    }
}
