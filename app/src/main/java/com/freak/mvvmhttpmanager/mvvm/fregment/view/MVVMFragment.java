package com.freak.mvvmhttpmanager.mvvm.fregment.view;


import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.freak.httpmanager.event.LiveBus;
import com.freak.httpmanager.log.LogUtil;
import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.fragment.AbstractLifecycleWithDatabindingFragment;
import com.freak.mvvmhttpmanager.databinding.FragmentMvvmBinding;
import com.freak.mvvmhttpmanager.mvvm.activity.model.LoginBean;
import com.freak.mvvmhttpmanager.mvvm.fregment.viewmodel.MVVMViewModel;

/**
 * @author Freak
 * @date 2019/5/16.
 */

public class MVVMFragment extends AbstractLifecycleWithDatabindingFragment<MVVMViewModel, FragmentMvvmBinding> implements View.OnClickListener {
    private EditText username, pwd;

    private TextView mvvm_result;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mvvm;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInVisible() {

    }

    @Override
    public void dataObserver() {
        LiveBus.getDefault().subscribe(Constants.EVENT_KEY_WORK_FRAGMENT, LoginBean.class).observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean loginBean) {
                LogUtil.e(loginBean.toString());
                mDataBinding.setLogin(loginBean);
            }
        });
        LiveBus.getDefault().subscribe(Constants.EVENT_KEY_WORK1_FRAGMENT, LoginBean.class).observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean loginBean) {
                LogUtil.e(loginBean.toString());
                mDataBinding.setLogin(loginBean);
            }
        });
    }

    @Override
    protected void initView() {
        LogUtil.e("初始化");

        username = getViewById(R.id.mvvm_username);
        pwd = getViewById(R.id.mvvm_pwd);
        mvvm_result = getViewById(R.id.mvvm_result);
        getViewById(R.id.login_mvvm_s).setOnClickListener(this);
        getViewById(R.id.login_mvvm).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_mvvm:
                mViewModel.doLogin1(username.getText().toString().trim(), pwd.getText().toString().trim());
                break;
            case R.id.login_mvvm_s:
                mViewModel.doLogin2(username.getText().toString().trim(), pwd.getText().toString().trim());
                break;
            default:
                break;
        }
    }
}
