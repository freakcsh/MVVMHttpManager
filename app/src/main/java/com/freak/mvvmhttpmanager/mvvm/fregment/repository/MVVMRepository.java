package com.freak.mvvmhttpmanager.mvvm.fregment.repository;


import com.freak.httpmanager.callback.ApiCallback;
import com.freak.httpmanager.callback.DisposableSubscriberCallBack;
import com.freak.httpmanager.callback.SubscriberCallBack;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.repository.BaseRepository;
import com.freak.mvvmhttpmanager.mvvm.activity.model.LoginBean;
import com.freak.mvvmhttpmanager.net.log.LogUtil;
import com.freak.mvvmhttpmanager.net.response.HttpResultFunc;
import com.freak.mvvmhttpmanager.util.ToastUtil;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author Freak
 * @date 2019/5/15.
 */

public class MVVMRepository extends BaseRepository {
    public void doLogin1(String userName, String pwd) {
        Flowable flowable = apiService.login2(userName, pwd).map(new HttpResultFunc<LoginBean>());
        addDisposable((Disposable) addSubscriber(flowable, new DisposableSubscriberCallBack<LoginBean>(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                LogUtil.e(model.toString());
                postData(Constants.EVENT_KEY_WORK_FRAGMENT, model);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
            }
        })));
    }

    public void doLogin2(String userName, String pwd) {
        Observable<LoginBean> observable = apiService.login5(userName, pwd).map(new HttpResultFunc<LoginBean>());
        addSubscription(observable, new SubscriberCallBack<LoginBean>(new ApiCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean model) {
                LogUtil.e(model.toString());
                postData(Constants.EVENT_KEY_WORK1_FRAGMENT, model);
            }

            @Override
            public void onFailure(String msg) {
                ToastUtil.shortShow(msg);
            }
        }));
    }
}
