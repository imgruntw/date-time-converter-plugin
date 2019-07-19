package de.imgruntw.datetimeconverter;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;

public final class ChangeUnitPopupRowAction extends DumbAwareAction {

    private final DateTimeTableModel model;
    private final DateTimeColumnModel columns;

    public ChangeUnitPopupRowAction(@NotNull DateTimeTableModel model, @NotNull DateTimeColumnModel columns) {
        super("Change timestamp unit", "Change timestamp unit", AllIcons.Actions.ChangeView);

        this.model = model;
        this.columns = columns;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        final DefaultActionGroup group = new DefaultActionGroup();
        group.add(new ChangeUnitRowAction("Unix timestamp (ms)", model, columns, ChronoUnit.MILLIS));
        group.add(new ChangeUnitRowAction("Unix timestamp (sec)", model, columns, ChronoUnit.SECONDS));

        JBPopupFactory.getInstance()
                .createActionGroupPopup("Timestamp units",
                        group, anActionEvent.getDataContext(),
                        JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                        true)
                .showUnderneathOf(anActionEvent.getInputEvent().getComponent());
    }
}
