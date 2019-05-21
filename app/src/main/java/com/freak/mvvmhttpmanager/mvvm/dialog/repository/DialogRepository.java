package com.freak.mvvmhttpmanager.mvvm.dialog.repository;

import com.freak.httpmanager.callback.ApiCallback;
import com.freak.httpmanager.callback.SubscriberCallBack;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.repository.BaseRepository;
import com.freak.mvvmhttpmanager.mvvm.dialog.model.DialogModel;
import com.freak.mvvmhttpmanager.net.log.LogUtil;
import com.freak.mvvmhttpmanager.net.response.HttpResultFunc;

import io.reactivex.Observable;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class DialogRepository extends BaseRepository {
    public void getDialogMessage() {
        Observable<DialogModel> observable = apiService.getDialogMessage().map(new HttpResultFunc<DialogModel>());
        addSubscription(observable, new SubscriberCallBack<DialogModel>(new ApiCallback<DialogModel>() {
            @Override
            public void onSuccess(DialogModel model) {
                LogUtil.e(model.toString());
                postData(Constants.DIALOG, model);
            }

            @Override
            public void onFailure(String msg) {
                LogUtil.e(msg);
                postData(Constants.DIALOG, null);
            }
        }));
    }
}
