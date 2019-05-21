package com.freak.mvvmhttpmanager.mvvm.dialog.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.freak.httpmanager.BaseViewModel;
import com.freak.mvvmhttpmanager.mvvm.dialog.repository.DialogRepository;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class DialogViewModel extends BaseViewModel<DialogRepository> {
    public DialogViewModel(@NonNull Application application) {
        super(application);
    }

    public void getDialogMessage() {
        mRepository.getDialogMessage();
    }
}
