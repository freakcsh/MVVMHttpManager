package com.freak.mvvmhttpmanager.mvvm.adapter.model;

/**
 * @author Freak
 * @date 2019/5/17.
 */

public class MVVMRecycleViewModel {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "MVVMRecycleViewModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
