package com.freak.mvvmhttpmanager.mvvm.activity.view;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.freak.httpmanager.event.LiveBus;
import com.freak.httpmanager.log.LogUtil;
import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.activity.AbstractLifecycleWithDatabindingActivity;
import com.freak.mvvmhttpmanager.databinding.ActivityMainBinding;
import com.freak.mvvmhttpmanager.mvvm.activity.model.LoginBean;
import com.freak.mvvmhttpmanager.mvvm.activity.viewmodel.MainViewModel;
import com.freak.mvvmhttpmanager.mvvm.adapter.view.MVVMRecycleViewActivity;
import com.freak.mvvmhttpmanager.mvvm.dialog.view.CommonDialogFragment;
import com.freak.mvvmhttpmanager.mvvm.fregment.MVVMFragmentActivity;
import com.freak.mvvmhttpmanager.mvvm.moredatabinding.view.MoreDatabindingActivity;
import com.freak.mvvmhttpmanager.util.DialogUtils;
import com.freak.mvvmhttpmanager.util.StateUtil;


/**
 * @author Freak
 * @date 2019/5/15
 */
public class MainActivity extends AbstractLifecycleWithDatabindingActivity<MainViewModel, ActivityMainBinding> implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private EditText username, pwd;
    private TextView tvResult;
    private Button rx_view;
    private Button recycle;
    private Button dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {


    }

    @Override
    protected void initView() {
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.login_s).setOnClickListener(this);
        findViewById(R.id.mvvm).setOnClickListener(this);
        findViewById(R.id.recycle).setOnClickListener(this);
        findViewById(R.id.dialog).setOnClickListener(this);
        findViewById(R.id.more_data).setOnClickListener(this);
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
                mDatabinding.setViewModel(loginBean);
            }
        });
        LiveBus.getDefault().subscribe(Constants.EVENT_KEY_WORK, LoginBean.class).observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean loginBean) {
                LogUtil.e(loginBean.toString());
                mDatabinding.setViewModel(loginBean);
            }
        });
    }

    @Override
    protected void transStatusColor() {
        StateUtil.transStatusColor(this, ContextCompat.getColor(this, R.color.colorAccent));
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
            case R.id.mvvm:
                startActivity(new Intent(this, MVVMFragmentActivity.class));
                break;
            case R.id.recycle:
                startActivity(new Intent(this, MVVMRecycleViewActivity.class));
                break;
            case R.id.dialog:
                DialogUtils.showCommonDialog(this, new CommonDialogFragment.OnTipsListener() {
                    @Override
                    public void onCancel() {
                        LogUtil.e("取消");
                    }

                    @Override
                    public void onSuccess() {
                        LogUtil.e("确定");
                    }
                });
                break;
            case R.id.more_data:
                startActivity(new Intent(this, MoreDatabindingActivity.class));
                break;
            default:
                break;
        }
    }
}
