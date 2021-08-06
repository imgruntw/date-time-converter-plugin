package de.imgruntw.datetimeconverter;

import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

public final class DateTimeConverterWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        final DateTimeConverterViewService viewService = ApplicationManager.getApplication().getService(DateTimeConverterViewService.class);

        viewService.initComponent();
        viewService.getModel().addRow(DateTimeUtil.getCurrentGasDayMs());

        final Content content = ContentFactory.SERVICE.getInstance().createContent(viewService.getComponent(), "", false);
        content.setCloseable(true);

        toolWindow.getContentManager().addContent(content);
    }
}
