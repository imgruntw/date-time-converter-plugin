package de.imgruntw.datetimeconverter;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.ui.table.JBTable;
import org.jetbrains.annotations.NotNull;

public final class PasteRowAction extends DumbAwareAction {

    private final DateTimeTableModel model;

    public PasteRowAction(@NotNull DateTimeTableModel model) {
        super("Paste", "Paste", AllIcons.Actions.Menu_paste);

        this.model = model;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        model.addRows(DateTimeUtil.getClipboardDateTimes());
    }
}
