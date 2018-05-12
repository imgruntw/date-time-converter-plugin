package de.imgruntw.datetimeconverter;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

public final class AddRowAction extends DumbAwareAction {

    private final DateTimeTableModel model;

    public AddRowAction(@NotNull DateTimeTableModel model) {
        super("Add", "Add", AllIcons.General.Add);

        this.model = model;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        model.addRow(DateTimeUtil.getCurrentGasDayMs());
    }
}
