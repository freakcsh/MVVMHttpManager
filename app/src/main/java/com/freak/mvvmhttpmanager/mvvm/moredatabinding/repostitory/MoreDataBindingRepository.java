package com.freak.mvvmhttpmanager.mvvm.moredatabinding.repostitory;

import com.freak.httpmanager.callback.ApiCallback;
import com.freak.httpmanager.callback.SubscriberCallBack;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.repository.BaseRepository;
import com.freak.mvvmhttpmanager.mvvm.moredatabinding.model.MoreDataBindingModel;
import com.freak.mvvmhttpmanager.mvvm.moredatabinding.model.MoreDataBindingModel2;
import com.freak.mvvmhttpmanager.net.log.LogUtil;
import com.freak.mvvmhttpmanager.net.response.HttpResultFunc;

import io.reactivex.Observable;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class MoreDataBindingRepository extends BaseRepository {
    public void getMoreDataBinding() {
        Observable<MoreDataBindingModel> observable = apiService.getMoreDataBinding().map(new HttpResultFunc<MoreDataBindingModel>());
        addSubscription(observable, new SubscriberCallBack<MoreDataBindingModel>(new ApiCallback<MoreDataBindingModel>() {
            @Override
            public void onSuccess(MoreDataBindingModel model) {
                LogUtil.e(model.toString());
                postData(Constants.MORE, model);
            }

            @Override
            public void onFailure(String msg) {
                LogUtil.e(msg);
            }
        }));

    }

    public void getMoreDataBinding2() {
        Observable<MoreDataBindingModel2> observable = apiService.getMoreDataBinding1().map(new HttpResultFunc<MoreDataBindingModel2>());
        addSubscription(observable, new SubscriberCallBack<MoreDataBindingModel2>(new ApiCallback<MoreDataBindingModel2>() {
            @Override
            public void onSuccess(MoreDataBindingModel2 model) {
                LogUtil.e(model.toString());
                postData(Constants.MORE1, model);
            }

            @Override
            public void onFailure(String msg) {
                LogUtil.e(msg);
            }
        }));
    }
}
