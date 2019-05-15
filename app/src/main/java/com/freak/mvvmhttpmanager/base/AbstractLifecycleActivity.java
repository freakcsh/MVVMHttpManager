package com.freak.mvvmhttpmanager.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.freak.httpmanager.BaseViewModel;
import com.freak.httpmanager.utils.TUtil;


/**
 * @author Freak
 * @date 2019/5/15
 */
@SuppressWarnings("ALL")
public abstract class AbstractLifecycleActivity<T extends BaseViewModel> extends AbstractBaseActivity {

    protected T mViewModel;

    public AbstractLifecycleActivity() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        dataObserver();
    }


    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);
    }

    protected void dataObserver() {

    }




//    protected void showSuccess() {
//        loadManager.showSuccess();
//    }
//
//    protected void showLoading() {
//        loadManager.showStateView(LoadingState.class);
//    }
//
//
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
//                }
//            }
//        }
//    };
}
