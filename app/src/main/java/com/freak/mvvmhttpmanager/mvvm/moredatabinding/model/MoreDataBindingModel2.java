package com.freak.mvvmhttpmanager.mvvm.moredatabinding.model;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class MoreDataBindingModel2 {
    private String username;
    private String msg;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MoreDataBindingModel2{" +
                "username='" + username + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
