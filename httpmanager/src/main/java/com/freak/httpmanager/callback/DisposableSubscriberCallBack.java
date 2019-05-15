package com.freak.httpmanager.callback;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;


/**
 * @author freak
 * @date 2019/01/25
 */
public class DisposableSubscriberCallBack<T> extends DisposableSubscriber<T> {
    private ApiCallback<T> apiCallback;

    public DisposableSubscriberCallBack(ApiCallback<T> apiCallback) {
        this.apiCallback = apiCallback;
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    /**
     * 错误时调用
     *
     * @param e
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onError(Throwable e) {
        try {
            String msg;
            if (e instanceof SocketTimeoutException) {
                msg = "连接服务器超时,请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof ConnectException) {
                msg = "网络中断，请检查您的网络状态";
                apiCallback.onFailure(msg);
            }else if (e instanceof UnknownHostException) {
                msg = "没有网络";
                apiCallback.onFailure(msg);
            } else if (e instanceof HttpException) {
                msg = "网络错误";
                apiCallback.onFailure(msg);
            } else if (e instanceof TimeoutException) {
                msg = "连接超时，请检查您的网络状态";
                apiCallback.onFailure(msg);
            } else if (e instanceof JsonSyntaxException) {
                Log.e("DisposableSubscriberCallBack", "JSON解析错误，请查看JSON结构", e);
                e.printStackTrace();
                apiCallback.onFailure(e.getMessage());
            } else {
                apiCallback.onFailure(e.getMessage());
            }
        } catch (Exception e1) {
            apiCallback.onFailure(e1.getMessage());
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }


    /**
     * 成功时调用
     *
     * @param o
     */
    @Override
    public void onNext(T o) {
        apiCallback.onSuccess(o);
    }

}
