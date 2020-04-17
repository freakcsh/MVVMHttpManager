package com.freak.mvvmhttpmanager.mvvm.adapter.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;

import com.freak.httpmanager.BaseViewModel;
import com.freak.mvvmhttpmanager.mvvm.adapter.repository.MVVMRecycleViewRepository;

/**
 * @author Freak
 * @date 2019/5/17.
 */

public class MVVMRecycleViewViewModel extends BaseViewModel<MVVMRecycleViewRepository> {
    public MVVMRecycleViewViewModel(@NonNull Application application) {
        super(application);
    }

    public void getList() {
        mRepository.getList();
    }
}
