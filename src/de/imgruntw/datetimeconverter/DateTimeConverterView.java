package de.imgruntw.datetimeconverter;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.actionSystem.ex.ActionUtil;
import com.intellij.openapi.application.ex.ClipboardUtil;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.KeyStrokeAdapter;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class DateTimeConverterView implements ApplicationComponent {

    private JComponent component;
    private DateTimeTableModel model;

    @Override
    public void initComponent() {
        model = new DateTimeTableModel();

        final KeyStroke shortcutPaste = KeymapUtil.getKeyStroke(CommonShortcuts.getPaste());
        final DateTimeColumnModel columns = new DateTimeColumnModel("Unix timestamp (ms)", "Datetime", "Format");
        final JBTable table = new JBTable(model, columns);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.registerKeyboardAction(e -> model.addRows(DateTimeUtil.getClipboardDateTimes()), shortcutPaste, JComponent.WHEN_FOCUSED);

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
