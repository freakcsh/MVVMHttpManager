package com.freak.mvvm;

/**
 * Module异步回调结果封装
 * @param <T>
 */
public class ModuleResult<T> {
    public final Throwable error;
    public final T data;

    public ModuleResult(T data, Throwable error) {
        this.data = data;
        this.error = error;
    }
}

