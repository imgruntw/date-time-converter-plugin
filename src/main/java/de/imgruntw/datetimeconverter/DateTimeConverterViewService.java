package de.imgruntw.datetimeconverter;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.CommonShortcuts;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@Service
public final class DateTimeConverterViewService {

    private JComponent component;
    private DateTimeTableModel model;

    public void initComponent() {
        model = new DateTimeTableModel();

        final KeyStroke shortcutPaste = KeymapUtil.getKeyStroke(CommonShortcuts.getPaste());
        final DateTimeColumnModel columns = new DateTimeColumnModel("Unix timestamp (ms)", "Datetime", "Format", "Time zone");

        final ComboBox<String> defaultPatterns = new ComboBox<>(DateTimePattern.getPatterns());
        defaultPatterns.setEditable(true);
        columns.getColumn(Column.FORMAT.getIndex()).setCellEditor(new DefaultCellEditor(defaultPatterns));

        final ComboBox<String> defaultTimeZones = new ComboBox<>(TimeZoneId.getIds());
        defaultTimeZones.setEditable(true);
        columns.getColumn(Column.TIME_ZONE.getIndex()).setCellEditor(new DefaultCellEditor(defaultTimeZones));

        final JBTable table = new JBTable(model, columns);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.registerKeyboardAction(e -> model.addRows(DateTimeUtil.getClipboardDateTimes()), shortcutPaste, JComponent.WHEN_FOCUSED);
        columns.setTableHeader(table.getTableHeader());

        final JBScrollPane scrollPane = new JBScrollPane(table);

        final DefaultActionGroup group = new DefaultActionGroup();
        group.add(new AddRowAction(model));
        group.add(new DeleteRowAction(model, table));
        group.add(new PasteRowAction(model));
        group.add(new ChangeUnitPopupRowAction(model, columns));

        final SimpleToolWindowPanel panel = new SimpleToolWindowPanel(false);

        final ActionToolbar toolbar = ActionManager.getInstance().createActionToolbar("datetimeconverterbar", group, false);
        toolbar.setTargetComponent(panel);

        panel.add(scrollPane);
        panel.setToolbar(toolbar.getComponent());

        component = panel;
    }

    public JComponent getComponent() {
        return component;
    }

    public DateTimeTableModel getModel() {
        return model;
    }
}
