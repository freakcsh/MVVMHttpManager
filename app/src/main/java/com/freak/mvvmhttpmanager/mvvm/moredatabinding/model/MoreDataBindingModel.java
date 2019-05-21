package com.freak.mvvmhttpmanager.mvvm.moredatabinding.model;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class MoreDataBindingModel {
    private String title;
    private String context;

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

    @Override
    public String toString() {
        return "MoreDataBindingModel{" +
                "title='" + title + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
