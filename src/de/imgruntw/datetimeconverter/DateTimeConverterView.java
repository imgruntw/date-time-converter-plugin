package de.imgruntw.datetimeconverter;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class DateTimeConverterView implements ApplicationComponent {

    private JComponent component;
    private DateTimeTableModel model;

    @Override
    public void initComponent() {
        model = new DateTimeTableModel();

        final DateTimeColumnModel columns = new DateTimeColumnModel("Unix timestamp (ms)", "Datetime", "Format");
        final JBTable table = new JBTable(model, columns);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);

        final JBScrollPane scrollPane = new JBScrollPane(table);

        final DefaultActionGroup group = new DefaultActionGroup();
        group.add(new AddRowAction(model));
        group.add(new DeleteRowAction(model, table));

        final SimpleToolWindowPanel panel = new SimpleToolWindowPanel(false);

        final ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("datetimeconverterbar", group, false);
        toolbar.setTargetComponent(panel);

        panel.add(scrollPane);
        panel.setToolbar(toolbar.getComponent());

        component = panel;
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "DateTimeConverterView";
    }

    public JComponent getComponent() {
        return component;
    }

    public DateTimeTableModel getModel() {
        return model;
    }
}
