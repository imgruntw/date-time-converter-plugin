package de.imgruntw.datetimeconverter;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

public final class PasteRowAction extends DumbAwareAction {

    private final DateTimeTableModel model;

    public PasteRowAction(@NotNull DateTimeTableModel model) {
        super("Paste", "Paste", AllIcons.Actions.MenuPaste);

        this.model = model;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        model.addRows(DateTimeUtil.getClipboardDateTimes());
    }
}
