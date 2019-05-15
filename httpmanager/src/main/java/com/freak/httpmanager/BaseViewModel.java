package com.freak.httpmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.freak.httpmanager.utils.TUtil;

/**
 * @author freak
 * @date 2019/01/25
 */

public class BaseViewModel<T extends AbstractRepository> extends AndroidViewModel {
    public T mRepository;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mRepository = TUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
            mRepository.unSubscribe();
        }

    }
}
