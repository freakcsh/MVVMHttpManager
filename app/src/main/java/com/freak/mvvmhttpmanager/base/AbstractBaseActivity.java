package com.freak.mvvmhttpmanager.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * @author Administrator
 */
public abstract class AbstractBaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏
        initStatusBar();
        //设置布局内容
        setContentView(getLayoutId());

        //初始化控件
        initViews(savedInstanceState);

    }


    public abstract void initStatusBar();


    /**
     * 设置布局layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化views
     *
     * @param savedInstanceState
     */
    public abstract void initViews(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

