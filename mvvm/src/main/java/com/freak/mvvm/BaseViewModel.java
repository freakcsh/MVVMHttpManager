package com.freak.mvvm;

import android.arch.lifecycle.ViewModel;

import com.freak.mvvm.ModuleDelegate;

/**
 * 基础ViewModel，可自动取消Module的调用
 */
public class BaseViewModel extends ViewModel {
    private ModuleDelegate mDelegate = new ModuleDelegate();

    protected <T extends BaseModule> T getModule(Class<T> moduleClass) {
        return mDelegate.getModule(moduleClass);
    }

    @Override
    protected void onCleared() {
        mDelegate.cancelAll();
        super.onCleared();
    }
}
