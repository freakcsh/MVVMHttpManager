package com.freak.mvvmhttpmanager.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.freak.httpmanager.BaseViewModel;
import com.freak.httpmanager.utils.TUtil;
import com.freak.mvvmhttpmanager.app.App;


/**
 * @author freak
 * @date 2019/5/16
 * MVVM activity基类
 */

@SuppressWarnings("ALL")
public abstract class AbstractLifecycleWithDatabindingActivity<T extends BaseViewModel, D extends ViewDataBinding> extends AppCompatActivity {
    protected T mViewModel;
    protected D mDatabinding;
    protected Activity mActivity;

    /**
     * 绑定布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化事件和数据
     */
    protected abstract void initEventAndData();

    /**
     * 控件初始化
     */
    protected abstract void initView();

    /**
     * 数据绑定
     */
    protected abstract void dataObserver();

    /**
     * 修改状态栏颜色
     */
    protected abstract void transStatusColor();


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mViewModel = viewModelProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(getLayoutId());
        super.onCreate(savedInstanceState);
        mActivity = this;
        //活动控制器
        App.getInstance().addActivity(this);
        mDatabinding = DataBindingUtil.setContentView(this, getLayoutId());
        initView();
        transStatusColor();
        initEventAndData();
        dataObserver();
    }

    protected <T extends ViewModel> T viewModelProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * presenter 解除view订阅
         */

        App.getInstance().removeActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * 打开一个Activity 默认 不关闭当前activity
     *
     * @param className
     */
    public void gotoActivity(Class<?> className) {
        gotoActivity(className, false, null);
    }

    /**
     * 打开一个Activity，并控制是否finish
     *
     * @param className              className
     * @param isCloseCurrentActivity 是否关闭
     */
    public void gotoActivity(Class<?> className, boolean isCloseCurrentActivity) {
        gotoActivity(className, isCloseCurrentActivity, null);
    }

    /**
     * 打开一个activity，并传递数据
     *
     * @param className              className
     * @param isCloseCurrentActivity 是否关闭
     * @param bundle                 bundle数据
     */
    public void gotoActivity(Class<?> className, boolean isCloseCurrentActivity, Bundle bundle) {
        Intent intent = new Intent(this, className);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        if (isCloseCurrentActivity) {
            finish();
        }
    }

    /**
     * 系统返回键监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        return (T) findViewById(id);
    }
}