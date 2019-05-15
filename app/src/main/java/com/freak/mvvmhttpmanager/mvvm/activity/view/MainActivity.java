package com.freak.mvvmhttpmanager.mvvm.activity.view;


import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.freak.httpmanager.event.LiveBus;
import com.freak.httpmanager.log.LogUtil;
import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.base.AbstractLifecycleActivity;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.databinding.ActivityMainBinding;
import com.freak.mvvmhttpmanager.mvvm.activity.model.LoginBean;
import com.freak.mvvmhttpmanager.mvvm.activity.viewmodel.MainViewModel;

import io.reactivex.disposables.Disposable;


/**
 * @author Freak
 * @date 2019/5/15
 */
public class MainActivity extends AbstractLifecycleActivity<MainViewModel> implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;
    private Disposable mSubscribe;
    private Button rx_view;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    public void initStatusBar() {
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mActivityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        initEventAndData();
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.login_s).setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initEventAndData() {
        username = findViewById(R.id.username);
        rx_view = findViewById(R.id.rx_view);
        pwd = findViewById(R.id.pwd);
        tvResult = findViewById(R.id.result);

    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe(Constants.EVENT_KEY_WORK1, LoginBean.class).observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean loginBean) {
                LogUtil.e(loginBean.toString());
                mActivityMainBinding.setViewModel(loginBean);
            }
        });
        LiveBus.getDefault().subscribe(Constants.EVENT_KEY_WORK, LoginBean.class).observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean loginBean) {
                LogUtil.e(loginBean.toString());
                mActivityMainBinding.setViewModel(loginBean);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null) {
            if (mSubscribe.isDisposed()) {
                mSubscribe.dispose();
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mViewModel.doLogin1(username.getText().toString().trim(), pwd.getText().toString().trim());
                break;
            case R.id.login_s:
                LogUtil.e("点击");
                mViewModel.doLogin2(username.getText().toString().trim(), pwd.getText().toString().trim());
                break;
            default:
                break;
        }
    }
}
