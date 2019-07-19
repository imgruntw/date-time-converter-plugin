package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.AbstractTableModel;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DateTimeTableModel extends AbstractTableModel {

    private final List<Row> rows;

    private ChronoUnit unit;

    public DateTimeTableModel() {
        this.rows = new ArrayList<>();
        this.unit = ChronoUnit.MILLIS;
    }

    public void addRow(long ms) {
        rows.add(Row.ofDefault(unit, ms));
        fireTableDataChanged();
    }

    public void addRows(@NotNull Collection<Long> ms) {
        ms.forEach(i -> rows.add(Row.ofDefault(unit, i)));
        fireTableDataChanged();
    }

    public void deleteRow(int index) {
        rows.remove(index);
        fireTableDataChanged();
    }

    public void setUnit(@NotNull ChronoUnit unit) {
        this.unit = unit;

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            final Row currentRow = rows.get(rowIndex);
            final Row updatedRow = Row.ofCopy(currentRow, unit);
            rows.set(rowIndex, updatedRow);
        }

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
        final Column column = Column.of(columnIndex).orElseThrow(IllegalArgumentException::new);

        switch (column) {
            case VALUE:
                return row.getRepresentationValue();
            case DATE_TIME:
                return row.getText();
            case FORMAT:
                return row.getFormat();
            case TIME_ZONE:
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
        final String currentFormat = currentRow.getFormat();
        final String currentTimeZoneId = currentRow.getTimeZoneId();
        final Column column = Column.of(columnIndex).orElseThrow(IllegalArgumentException::new);
        final Row row;

        switch (column) {
            case VALUE:
                if (DateTimeUtil.isLong(text)) {
                    row = Row.ofRepresentationValue(currentRow, unit, Long.parseLong(text));
                } else {
                    row = currentRow;
                }
                break;
            case DATE_TIME:
                if (DateTimeUtil.isDateTime(text, currentFormat)) {
                    row = Row.ofMs(currentRow, unit, DateTimeUtil.toMs(text, currentFormat, currentTimeZoneId));
                } else {
                    row = currentRow;
                }
                break;
            case FORMAT:
                if (DateTimeUtil.isFormat(text)) {
                    row = Row.ofFormat(currentRow, unit, text);
                } else {
                    row = currentRow;
                }
                break;
            case TIME_ZONE:
                if (DateTimeUtil.isTimeZoneId(text)) {
                    row = Row.ofTimeZoneId(currentRow, unit, text);
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
