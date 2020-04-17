package com.freak.mvvmhttpmanager.mvvm.moredatabinding.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;

import com.freak.httpmanager.BaseViewModel;
import com.freak.mvvmhttpmanager.mvvm.moredatabinding.repostitory.MoreDataBindingRepository;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class MoreDataBindingViewModel extends BaseViewModel<MoreDataBindingRepository> {
    public MoreDataBindingViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMoreDataBinding() {
        mRepository.getMoreDataBinding();
    }

    public void getMoreDataBinding2() {
        mRepository.getMoreDataBinding2();
    }

}
