package com.freak.mvvmhttpmanager.mvvm.adapter.repository;

import com.freak.httpmanager.callback.ApiCallback;
import com.freak.httpmanager.callback.SubscriberCallBack;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.repository.BaseRepository;
import com.freak.mvvmhttpmanager.net.log.LogUtil;
import com.freak.mvvmhttpmanager.net.response.HttpResult;
import com.freak.mvvmhttpmanager.util.ToastUtil;

import io.reactivex.Observable;

/**
 * @author Freak
 * @date 2019/5/17.
 */

public class MVVMRecycleViewRepository extends BaseRepository {
    public void getList() {
        Observable<HttpResult> observable = apiService.getList();
        addSubscription(observable, new SubscriberCallBack<HttpResult>(new ApiCallback<HttpResult>() {
            @Override
            public void onSuccess(HttpResult model) {
                LogUtil.e(model.toString());
                postData(Constants.LIST, model);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
                postData(Constants.LIST, null);
            }
        }));
    }
}
