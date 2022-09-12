package com.qg.matrix.core.interfaces;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.qg.matrix.core.manager.BeanManager;
import com.qg.matrix.core.model.BaseParamModel;
import com.qg.matrix.core.model.BaseResultModel;
import com.qg.matrix.core.utils.JDKProxyManager;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 扩展点抽象类，扩展点实现必须继承该类
 * 1、先调用getExtension 初始化
 * 2、再调用invoke 回调run方法
 */
@Getter
@Setter
public abstract class AbstractExt {

    public static TransmittableThreadLocal<BaseResultModel> resultContext = new TransmittableThreadLocal<>();

    private List<?> beanList = new ArrayList<>();

    private BaseParamModel param;

    private BaseResultModel result;

    /***
     * 根据入参
     * @param clazz 扩展点接口定义
     * @param baseParamModel
     * @param result
     * @param <T>
     */
    public <T> List<?> getExtension(Class<T> clazz, BaseParamModel baseParamModel, BaseResultModel result) {
        this.param = baseParamModel;
        this.result = result;
        resultContext.set(result);
        Map<String, List<Object>> appContextBeanMap = BeanManager.appContext.get();
        List<Object> originBeans = new ArrayList<>();

        appContextBeanMap.keySet().forEach(k -> {
            if (Objects.equals(k, baseParamModel.getIdentity())) {
                originBeans.addAll(appContextBeanMap.get(k));
            }
        });
        /**
         * 获取扩展点接口的实现类
         */
        beanList = originBeans.stream().filter(clazz::isInstance).map(bean -> (T) bean).collect(Collectors.toList());

        return beanList;
    }

    /**
     * 执行扩展点逻辑
     * @param methodName 要执行的方法名
     * @param beanList 扩展点实现bean
     * @return
     */
    public Object invoke(String methodName,  List<?> beanList) {

        beanList.forEach(e -> {
            try {
                // 反射获得所有方法名
                Method[] methods = e.getClass().getMethods();
                for (Method m : methods) {
                   if (m.getName().equalsIgnoreCase(methodName)) {
                      result =  (BaseResultModel)m.invoke(e, this.param);
                   }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return this.result;
    }

    //abstract BaseResultModel run(Object param, BaseResultModel result);


}
