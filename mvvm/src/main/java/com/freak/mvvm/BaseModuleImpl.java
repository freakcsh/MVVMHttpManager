package com.freak.mvvm;

/**
 * 基础模块实现类，所有模块实现类须继承该类
 */
public class BaseModuleImpl {

    /**
     * 获取其他模块实现类
     * @param moduleClass
     * @param <T>
     */
    protected <T extends BaseModuleImpl> T getModule(Class<T> moduleClass) {
        if (getClass().equals(moduleClass)) {
            return (T) this;
        }
        return ModuleManager.getImpl(moduleClass);
    }

}
