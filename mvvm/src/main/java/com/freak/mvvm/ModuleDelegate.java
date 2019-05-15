package com.freak.mvvm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Module委托类 通过该类获取的Module，会统一收集返回的ModuleCall
 * 可进行批量取消
 */
public class ModuleDelegate {
    private static Map<Class<?>, BaseModule> mModuleCache = new ConcurrentHashMap<>();
    private ModuleCalls mModuleCalls = new ModuleCalls();

    public <T extends BaseModule> T getModule(Class<T> moduleClass) {
        BaseModule module = mModuleCache.get(moduleClass);
        if (module != null) {
            return (T) module;
        }
        synchronized (mModuleCache) {
            module = mModuleCache.get(moduleClass);
            if (module == null) {
                InvocationHandler invocationHandler = new ModuleInvocationHandler(ModuleManager.get(moduleClass), mModuleCalls);
                module = (T) Proxy.newProxyInstance(moduleClass.getClassLoader(), new Class[]{moduleClass}, invocationHandler);
                mModuleCache.put(moduleClass, module);
            }
            return (T) module;
        }
    }

    public void cancelAll() {
        mModuleCalls.cancel();
        // 清理缓存的module
        synchronized (mModuleCache) {
            mModuleCache.clear();
        }
    }

    private static class ModuleInvocationHandler implements InvocationHandler {
        private Object mTarget;
        private ModuleCalls mModuleCalls;

        public ModuleInvocationHandler(Object target, ModuleCalls moduleCalls) {
            mTarget = target;
            mModuleCalls = moduleCalls;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object res = method.invoke(mTarget, args);
            if (ModuleCall.class.equals(method.getReturnType())) { // 收集返回的moduleCall
                mModuleCalls.add((ModuleCall) res);
            }
            return res;
        }
    }

    private static class ModuleCalls {
        private List<ModuleCall> mModuleCalls;

        public void add(ModuleCall call) {
            if (call == null) {
                return;
            }
            if (mModuleCalls == null) {
                synchronized (this) {
                    if (mModuleCalls == null) {
                        mModuleCalls = new LinkedList<>();
                    }
                }
            }

            if (mModuleCalls.size() >= 5) { // 及时移除已经完成的调用
                synchronized (this) {
                    Iterator<ModuleCall> iterator = mModuleCalls.iterator();
                    ModuleCall tmp;
                    while (iterator.hasNext()) {
                        tmp = iterator.next();
                        if (tmp.isDone()) {
                            iterator.remove();
                        }
                    }
                }
            }

            synchronized (this) {
                mModuleCalls.add(call);
            }
        }

        public void cancel() {
            if (mModuleCalls == null) {
                return;
            }
            synchronized (this) {
                for (ModuleCall call : mModuleCalls) {
                    call.cancel();
                }
                mModuleCalls.clear();
            }
        }
    }
}
