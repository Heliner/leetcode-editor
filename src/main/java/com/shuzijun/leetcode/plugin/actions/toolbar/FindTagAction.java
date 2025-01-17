package com.shuzijun.leetcode.plugin.actions.toolbar;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.shuzijun.leetcode.plugin.manager.ViewManager;
import com.shuzijun.leetcode.plugin.model.PluginConstant;
import com.shuzijun.leetcode.plugin.model.Tag;
import com.shuzijun.leetcode.plugin.utils.DataKeys;
import com.shuzijun.leetcode.plugin.window.NavigatorTable;
import com.shuzijun.leetcode.plugin.window.WindowFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author shuzijun
 */
public class FindTagAction extends ToggleAction {

    private Tag tag;

    private String filterKey;

    private boolean onlyOne;

    private List<Tag> typeTags;

    public FindTagAction(@Nullable String text, Tag tag, List<Tag> typeTags, boolean onlyOne, String filterKey) {
        super(text);
        this.tag = tag;
        this.typeTags = typeTags;
        this.onlyOne = onlyOne;
        this.filterKey = filterKey;
    }

    @Override
    public boolean isSelected(AnActionEvent anActionEvent) {
        return tag.isSelect();
    }

    @Override
    public void setSelected(AnActionEvent anActionEvent, boolean b) {
        if (onlyOne && b) {
            typeTags.forEach(tag -> tag.setSelect(false));
        }
        tag.setSelect(b);
        NavigatorTable navigatorTable = WindowFactory.getDataContext(anActionEvent.getProject()).getData(DataKeys.LEETCODE_PROJECTS_TREE);
        if (navigatorTable == null) {
            return;
        }
        ProgressManager.getInstance().run(new Task.Backgroundable(anActionEvent.getProject(), PluginConstant.PLUGIN_NAME + "." + tag.getName(), false) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                if ("categorySlug".equals(filterKey)) {
                    if (b) {
                        navigatorTable.getPageInfo().setCategorySlug(tag.getSlug());
                    } else {
                        navigatorTable.getPageInfo().setCategorySlug("");
                    }
                } else {
                    navigatorTable.getPageInfo().disposeFilters(filterKey, tag.getSlug(), b);
                }
                navigatorTable.getPageInfo().setPageIndex(1);
                ViewManager.loadServiceData(navigatorTable, anActionEvent.getProject());
            }
        });
    }


}
