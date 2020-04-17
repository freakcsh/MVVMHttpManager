package com.freak.mvvmhttpmanager.base.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freak.httpmanager.BaseViewModel;
import com.freak.httpmanager.utils.TUtil;


/**
 * @author freak
 * @date 2019/5/16
 * MVVM Fragment基类
 */

@SuppressWarnings("ALL")
public abstract class AbstractLifecycleFragment<T extends BaseViewModel> extends Fragment {
    protected T mViewModel;
    protected View mView;
    protected FragmentActivity mActivity;
    protected boolean mIsFirstVisible = true;

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 数据懒加载
     */
    protected abstract void lazyLoad();

    /**
     * 当界面不可见时的操作
     */
    protected abstract void onInVisible();

    /**
     * 数据绑定
     */
    protected abstract void dataObserver();

    /**
     * 控件初始化
     */
    protected abstract void initView();

    @Override
    public void onAttach(Context context) {
        mActivity = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), container, false);
        mViewModel = viewModelProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        if (null != mViewModel) {
            dataObserver();
        }
        initView();
        return mView;
    }

    /**
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    protected <T extends ViewModel> T viewModelProviders(AbstractLifecycleFragment fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isVis = isHidden() || getUserVisibleHint();
        if (isVis && mIsFirstVisible) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected void onVisible() {
        if (mIsFirstVisible && isResumed()) {
            lazyLoad();
            mIsFirstVisible = false;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.mActivity = null;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.mActivity = null;
    }

    /**
     * 资源文件绑定
     *
     * @param id  资源文件id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) mView.findViewById(id);
    }
}
