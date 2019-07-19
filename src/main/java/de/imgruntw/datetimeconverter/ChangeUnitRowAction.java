package de.imgruntw.datetimeconverter;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

import java.time.temporal.ChronoUnit;

public final class ChangeUnitRowAction extends DumbAwareAction {

    private final DateTimeTableModel model;
    private final DateTimeColumnModel columns;
    private final ChronoUnit unit;

    public ChangeUnitRowAction(
            @NotNull String name,
            @NotNull DateTimeTableModel model,
            @NotNull DateTimeColumnModel columns,
            @NotNull ChronoUnit unit) {
        super(name);

        this.model = model;
        this.columns = columns;
        this.unit = unit;
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {

        final String valueColumnName = getTemplatePresentation().getText();
        columns.setColumnHeader(Column.VALUE, valueColumnName);

        model.setUnit(unit);
    }
}
