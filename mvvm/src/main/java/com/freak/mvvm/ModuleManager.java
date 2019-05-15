package com.freak.mvvm;

import com.freak.mvvm.ProxyTarget;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Module管理类
 */
class ModuleManager {
    private static Map<Class<?>, BaseModule> mModuleCache = new ConcurrentHashMap<>();
    private static Map<Class<?>, BaseModuleImpl> mModuleImplCache = new ConcurrentHashMap<>();

    /**
     * 获取Module接口代理对象，同样类型的Module将获取到相同的对象
     */
    public static <T extends BaseModule> T get(Class<T> moduleClass) {
        BaseModule module = mModuleCache.get(moduleClass);
        if (module != null) {
            return (T) module;
        }
        synchronized (mModuleCache) {
            try {
                module = mModuleCache.get(moduleClass);
                if (module == null) {
                    module = create(moduleClass);
                    mModuleCache.put(moduleClass, module);
                }
                return (T) module;
            } catch (Throwable t) {
                throw new RuntimeException(String.format("获取%s失败 ", moduleClass.getSimpleName()), t);
            }
        }
    }

    /**
     * 获取Module实现类的实例，同样类型的Module实现类将获取到相同的对象
     */
    static <T extends BaseModuleImpl> T getImpl(Class<T> moduleClass) {
        BaseModuleImpl module = mModuleImplCache.get(moduleClass);
        if (module != null) {
            return (T) module;
        }
        synchronized (mModuleImplCache) {
            try {
                module = mModuleImplCache.get(moduleClass);
                if (module == null) {
                    module = moduleClass.newInstance();
                    mModuleImplCache.put(moduleClass, module);
                }
                return (T) module;
            } catch (Throwable t) {
                throw new RuntimeException("获取moduleImpl失败 " + moduleClass.getName() + "  " + t);
            }
        }
    }

    // 创建Module接口代理对象
    private static <T extends BaseModule> T create(Class<T> moduleClass) throws Exception {
        ProxyTarget proxyTarget = moduleClass.getAnnotation(ProxyTarget.class);
        Class<? extends BaseModuleImpl> targetClass = proxyTarget.value();
        Method[] methods = moduleClass.getDeclaredMethods();
        Method[] targetMethods = targetClass.getDeclaredMethods();

        HashMap<Method, Method> methodMap = new HashMap<>();
        boolean find;
        for (Method method : methods) {
            find = false;
            for (Method targetMethod : targetMethods) {
                if (methodEquals(method, targetMethod)) {
                    // 检查返回值
                    checkReturnType(method, targetMethod);
                    find = true;
                    methodMap.put(method, targetMethod);
                    break;
                }
            }
            if (!find) {
                throw new IllegalArgumentException(String.format("%s::%s没有对应的实现方法", method.getDeclaringClass().getSimpleName(), method.getName()));
            }
        }
        Object targetObject = getImpl(targetClass);
        return (T) Proxy.newProxyInstance(moduleClass.getClassLoader(), new Class[]{moduleClass}, new ModuleInvocationHandler(targetObject, methodMap));
    }

    private static class ModuleInvocationHandler implements InvocationHandler {
        private Object mTargetObject;
        private Map<Method, Method> mMethodMap;

        public ModuleInvocationHandler(Object targetObject, Map<Method, Method> methodMap) {
            mTargetObject = targetObject;
            mMethodMap = methodMap;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object res = mMethodMap.get(method).invoke(mTargetObject, args);
            if (ModuleCall.class.equals(method.getReturnType())) {
                ModuleCall call = new ModuleCall();
                call.setObservable(res);
                return call;
            } else {
                return res;
            }
        }
    }

    // 判断两个方法的签名是否相同
    private static boolean methodEquals(Method method1, Method method2) {
        if (!method1.getName().equals(method2.getName())) {
            return false;
        }
        Class<?>[] params1 = method1.getParameterTypes();
        Class<?>[] params2 = method2.getParameterTypes();
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    // 检查两个方法的返回类型是否符合约定规则
    private static void checkReturnType(Method method1, Method method2) {
        Class<?> returnType;
        Type returnType1, returnType2;
        if (ModuleCall.class.equals(method1.getReturnType())) { // 异步回调的方法
            returnType = method2.getReturnType();
            if (returnType.equals(Observable.class) || returnType.equals(Single.class) || returnType.equals(Flowable.class) || returnType.equals(Maybe.class)) {

                returnType1 = method1.getGenericReturnType();
                returnType2 = method2.getGenericReturnType();

                if (returnType1 instanceof ParameterizedType && returnType2 instanceof ParameterizedType) { // 都带泛型
                    // 检查泛型的类型是否一样
                    if (!((ParameterizedType) returnType1).getActualTypeArguments()[0].equals(((ParameterizedType) returnType2).getActualTypeArguments()[0])) {
                        throw new IllegalArgumentException(method1.getName() + "方法的返回值类型的泛型的须一样");
                    }
                } else if (!(returnType1 instanceof Class && returnType2 instanceof Class)) {
                    throw new IllegalArgumentException(method1.getName() + "方法的返回值类型的泛型的须一样");
                }
            } else {
                throw new IllegalArgumentException(String.format("%s::%s的返回值类型必须是Observable,Single,Flowable,Maybe之一", method2.getDeclaringClass().getSimpleName(), method2.getName()));
            }
        } else {
            if (!method1.getGenericReturnType().equals(method2.getGenericReturnType())) { //同步调用的返回值必须一样
                throw new IllegalArgumentException(method1.getName() + "方法的返回值类型不一样");
            }
        }
    }

}
