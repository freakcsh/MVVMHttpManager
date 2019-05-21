package com.freak.mvvmhttpmanager.base.dialog;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.freak.httpmanager.BaseViewModel;
import com.freak.httpmanager.utils.TUtil;
import com.freak.mvvmhttpmanager.R;


/**
 * MVVM模式的Base Dialog fragment
 *
 * @author freak
 * @date 2019/5/16
 */

@SuppressWarnings("ALL")
public abstract class AbstractLifecycleWithDatabindingDialogFragment<T extends BaseViewModel, D extends ViewDataBinding> extends DialogFragment {


    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            //防止连续点击add多个fragment
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected T mViewModel;
    protected D mDataBinding;
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


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.0f;
            window.setAttributes(layoutParams);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setStyle(R.style.dialog, 0);
        //设置背景透明
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCanceledOnTouchOutside(false);
        final Window window = getDialog().getWindow();
        assert window != null;

        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), ((ViewGroup) window.findViewById(android.R.id.content)), false);
        mView = mDataBinding.getRoot();
//        mView = inflater.inflate(getLayoutId(), ((ViewGroup) window.findViewById(android.R.id.content)),false);
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
    protected <T extends ViewModel> T viewModelProviders(AbstractLifecycleWithDatabindingDialogFragment fragment, @NonNull Class<T> modelClass) {
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
