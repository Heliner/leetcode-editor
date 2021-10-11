package com.shuzijun.leetcode.plugin.actions.tree;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.shuzijun.leetcode.plugin.manager.NoteManager;
import com.shuzijun.leetcode.plugin.model.Config;
import com.shuzijun.leetcode.plugin.model.Question;
import com.shuzijun.leetcode.plugin.window.NavigatorTable;

/**
 * @author shuzijun
 */
public class PullNoteAction extends  AbstractTreeAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent, Config config, NavigatorTable navigatorTable, Question question) {
        NoteManager.pull(question,anActionEvent.getProject());
        NoteManager.show(question,anActionEvent.getProject());
    }
}
