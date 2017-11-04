package de.imgruntw.datetimeconverter;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public final class DateTimeConverterWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        final DateTimeConverterView view = project.getComponent(DateTimeConverterView.class);
        view.getModel().addRow(DateTimeUtil.nowGasDay());

        final Content content = ContentFactory.SERVICE.getInstance().createContent(view.getComponent(), "", false);
        content.setCloseable(true);

        toolWindow.getContentManager().addContent(content);
    }
}
