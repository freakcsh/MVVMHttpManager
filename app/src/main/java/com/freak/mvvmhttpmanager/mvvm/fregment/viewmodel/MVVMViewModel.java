package com.freak.mvvmhttpmanager.mvvm.fregment.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.freak.httpmanager.BaseViewModel;
import com.freak.mvvmhttpmanager.mvvm.fregment.repository.MVVMRepository;

/**
 * @author Freak
 * @date 2019/5/16.
 */

public class MVVMViewModel extends BaseViewModel<MVVMRepository> {
    public MVVMViewModel(@NonNull Application application) {
        super(application);
    }

    public void doLogin1(String userName, String pwd) {
        mRepository.doLogin1(userName, pwd);
    }

    public void doLogin2(String userName, String pwd) {
        mRepository.doLogin2(userName, pwd);
    }
}
