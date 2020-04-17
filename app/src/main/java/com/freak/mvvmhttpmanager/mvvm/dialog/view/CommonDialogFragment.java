package com.freak.mvvmhttpmanager.mvvm.dialog.view;


import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.freak.httpmanager.event.LiveBus;
import com.freak.mvvmhttpmanager.R;
import com.freak.mvvmhttpmanager.app.Constants;
import com.freak.mvvmhttpmanager.base.dialog.AbstractLifecycleWithDatabindingDialogFragment;
import com.freak.mvvmhttpmanager.databinding.FragmentDialogCommonBinding;
import com.freak.mvvmhttpmanager.mvvm.dialog.model.DialogModel;
import com.freak.mvvmhttpmanager.mvvm.dialog.viewmodel.DialogViewModel;
import com.freak.mvvmhttpmanager.net.log.LogUtil;


/**
 * 显示系統提示的对话框，可以自行设置题目，内容，取消，成功
 *
 * @author Administrator
 */
public class CommonDialogFragment extends AbstractLifecycleWithDatabindingDialogFragment<DialogViewModel, FragmentDialogCommonBinding> implements View.OnClickListener {


    private TextView mTextViewCommonTitle;
    private TextView mTextViewCommonContext;
    private TextView mTextViewCancel;
    private TextView mTextViewCommit;

    private OnTipsListener onTipsListener;

    public void setOnConfirmListener(OnTipsListener onTipsListener) {
        this.onTipsListener = onTipsListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dialog_common;
    }

    @Override
    protected void lazyLoad() {
        mViewModel.getDialogMessage();
    }

    @Override
    protected void onInVisible() {

    }

    @Override
    protected void dataObserver() {
        LiveBus.getDefault().subscribe(Constants.DIALOG, DialogModel.class).observe(this, new Observer<DialogModel>() {
            @Override
            public void onChanged(@Nullable DialogModel dialogModel) {
                LogUtil.e(dialogModel.toString());
                if (dialogModel != null) {
                    mDataBinding.setDialogViewModel(dialogModel);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        setStyle(R.style.dialog,0);
        setCancelable(true);
        mTextViewCommonTitle = getViewById(R.id.text_view_common_title);
        mTextViewCommonContext = getViewById(R.id.text_view_common_context);
        mTextViewCancel = getViewById(R.id.text_view_cancel);
        mTextViewCommit = getViewById(R.id.text_view_commit);
        mTextViewCancel.setOnClickListener(this);
        mTextViewCommit.setOnClickListener(this);
    }

    public interface OnTipsListener {
        /**
         * 返回
         */
        void onCancel();

        /**
         * 确定
         *
         * @param
         */
        void onSuccess();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_cancel:
                dismiss();
                if (onTipsListener != null) {
                    onTipsListener.onCancel();
                }
                break;
            case R.id.text_view_commit:
                dismiss();
                if (onTipsListener != null) {
                    onTipsListener.onSuccess();
                }
                break;
            default:
                break;
        }
    }
}

