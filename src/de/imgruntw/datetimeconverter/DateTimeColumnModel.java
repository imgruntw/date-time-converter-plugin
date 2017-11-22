package de.imgruntw.datetimeconverter;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public final class DateTimeColumnModel extends DefaultTableColumnModel {

    public DateTimeColumnModel(String... names) {
        for (int i = 0; i < names.length; i++) {
            addColumn(i, names[i]);
        }
    }

    private void addColumn(int i, String name) {
        final TableColumn column = new TableColumn(i);
        column.setHeaderValue(name);
        addColumn(column);
    }
}
