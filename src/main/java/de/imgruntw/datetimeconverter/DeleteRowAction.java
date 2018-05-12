package de.imgruntw.datetimeconverter;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

public final class DeleteRowAction extends DumbAwareAction {

    private final DateTimeTableModel model;
    private final JBTable table;

    public DeleteRowAction(@NotNull DateTimeTableModel model, @NotNull JBTable table) {
        super("Delete", "Delete", AllIcons.General.Remove);

        this.model = model;
        this.table = table;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        final int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            model.deleteRow(selectedRow);
        }
    }
}
