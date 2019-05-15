package com.freak.mvvmhttpmanager.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.freak.httpmanager.BaseViewModel;
import com.freak.httpmanager.event.LiveBus;
import com.freak.httpmanager.utils.TUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Freak
 * @date 2019/5/15
 */
public abstract class AbstractLifecycleFragment<T extends BaseViewModel> extends AbstractBaseFragment {

    protected T mViewModel;

    private List<Object> eventKeys = new ArrayList<>();

    @Override
    public void initView(Bundle state) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
//        if (null != mViewModel) {
//            dataObserver();
//            mViewModel.mRepository.loadState.observe(this, observer);
//        }
    }

    /**
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    protected <T extends ViewModel> T VMProviders(AbstractBaseFragment
                                                          fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }

    protected <T> MutableLiveData<T> registerSubscriber(Object eventKey, Class<T> tClass) {

        return registerSubscriber(eventKey, null, tClass);
    }

    protected <T> MutableLiveData<T> registerSubscriber(Object eventKey, String tag, Class<T> tClass) {
        String event;
        if (TextUtils.isEmpty(tag)) {
            event = (String) eventKey;
        } else {
            event = eventKey + tag;
        }
        eventKeys.add(event);
        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
    }


//    @Override
//    protected void onStateRefresh() {
//        showLoading();
//    }
//

    /**
     * 获取网络数据
     */
    protected void getRemoteData() {

    }

//    protected void showError(Class<? extends BaseStateControl> stateView, Object tag) {
//        loadManager.showStateView(stateView, tag);
//    }
//
//    protected void showError(Class<? extends BaseStateControl> stateView) {
//        showError(stateView, null);
//    }
//
//    protected void showSuccess() {
//        loadManager.showSuccess();
//    }
//
//    protected void showLoading() {
//        loadManager.showStateView(LoadingState.class);
//    }
//
//
//    /**
//     * 状态页面监听
//     */
//    protected Observer observer = new Observer<String>() {
//        @Override
//        public void onChanged(@Nullable String state) {
//            if (!TextUtils.isEmpty(state)) {
//                if (StateConstants.ERROR_STATE.equals(state)) {
//                    showError(ErrorState.class, "2");
//                } else if (StateConstants.NET_WORK_STATE.equals(state)) {
//                    showError(ErrorState.class, "1");
//                } else if (StateConstants.LOADING_STATE.equals(state)) {
//                    showLoading();
//                } else if (StateConstants.SUCCESS_STATE.equals(state)) {
//                    showSuccess();
//                } else if (StateConstants.NOT_DATA_STATE.equals(state)) {
//                    showError(ErrorState.class, "0");
//                }
//            }
//        }
//    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearEvent();
    }

    private void clearEvent() {
        if (eventKeys != null && eventKeys.size() > 0) {
            for (int i = 0; i < eventKeys.size(); i++) {
                LiveBus.getDefault().clear(eventKeys.get(i));
            }
        }
    }
}
