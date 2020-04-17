package com.freak.mvvmhttpmanager.mvvm.activity.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;

import com.freak.httpmanager.BaseViewModel;
import com.freak.mvvmhttpmanager.mvvm.activity.repository.MainRepository;

/**
 *
 * @author Administrator
 * @date 2019/5/14
 */

public class MainViewModel extends BaseViewModel<MainRepository> {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void doLogin1(String userName, String pwd) {
        mRepository.doLogin1(userName,pwd);
    }
    public void doLogin2(String userName, String pwd) {
        mRepository.doLogin2(userName,pwd);
    }
}
