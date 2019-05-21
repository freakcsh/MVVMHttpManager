package com.freak.mvvmhttpmanager.mvvm.dialog.model;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class DialogModel {
    private String title;
    private String context;
    private String cancel;
    private String commit;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return "DialogModel{" +
                "title='" + title + '\'' +
                ", context='" + context + '\'' +
                ", cancel='" + cancel + '\'' +
                ", commit='" + commit + '\'' +
                '}';
    }
}
