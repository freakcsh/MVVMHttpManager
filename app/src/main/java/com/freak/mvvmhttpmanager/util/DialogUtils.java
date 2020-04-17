package com.freak.mvvmhttpmanager.util;

import androidx.appcompat.app.AppCompatActivity;

import com.freak.mvvmhttpmanager.mvvm.dialog.view.CommonDialogFragment;

/**
 * @author Freak
 * @date 2019/5/21.
 */

public class DialogUtils {
    public static void showCommonDialog(AppCompatActivity activity, CommonDialogFragment.OnTipsListener onTipsListener) {
        CommonDialogFragment dialogFragment = new CommonDialogFragment();
        dialogFragment.setCancelable(false);
        dialogFragment.setOnConfirmListener(onTipsListener);
        dialogFragment.show(activity.getSupportFragmentManager(), "");
    }
}
