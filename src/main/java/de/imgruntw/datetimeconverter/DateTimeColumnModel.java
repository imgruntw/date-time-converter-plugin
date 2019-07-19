package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public final class DateTimeColumnModel extends DefaultTableColumnModel {

    private JTableHeader tableHeader;

    public DateTimeColumnModel(@NotNull String... names) {
        for (int i = 0; i < names.length; i++) {
            addColumn(i, names[i]);
        }
    }

    public void setColumnHeader(Column column, @NotNull String name) {
        getColumn(column.getIndex()).setHeaderValue(name);
        tableHeader.repaint();
    }

    public void setTableHeader(JTableHeader tableHeader) {
        this.tableHeader = tableHeader;
    }

    private void addColumn(int i, String name) {
        final TableColumn column = new TableColumn(i);
        column.setHeaderValue(name);
        addColumn(column);
    }
}
