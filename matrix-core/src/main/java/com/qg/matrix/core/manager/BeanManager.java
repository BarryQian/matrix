package com.qg.matrix.core.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.qg.matrix.core.annotations.Extension;
import com.qg.matrix.core.model.MatrixExt;
import com.qg.matrix.core.model.MatrixJson;
import com.qg.matrix.core.utils.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据matrix-json文件的配置，组装该身份下使用到的spring bean
 */
@Component
@Slf4j
public class BeanManager implements ApplicationContextAware {

    /**
     * 各个业务身份的上下文
     */
    public static TransmittableThreadLocal<Map<String/**identity**/, List<Object>>> appContext = new TransmittableThreadLocal<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        MatrixJson matrixJson = ResourceUtil.readMatrixExtJson();

        String identity = matrixJson.getIdentity();
        List<MatrixExt> matrixExtList = matrixJson.getMatrixExtList();

        List<Object> beanList = new ArrayList<>();
        Map<String, Object> objectMap = applicationContext.getBeansWithAnnotation(Extension.class);

        List<Object> beanContainer = objectMap.values().stream().filter(bean -> {
            Extension annotation = bean.getClass().getAnnotation(Extension.class);
            // 识别身份
            if (identity.equals(annotation.identity())) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());

        matrixExtList.forEach(ext -> {
            ext.getTypeList().forEach( type -> {
                beanContainer.forEach( bean ->{
                    //扩展点编码与类型相同，则获取
                    Extension annotation = bean.getClass().getAnnotation(Extension.class);
                    if (annotation.code().equals(ext.getExtCode()) && annotation.type().equalsIgnoreCase(type)) {
                        beanList.add(bean);
                        return;
                    }
                });

                });
            });
//        objectMap.values().forEach(bean -> {
//            // 获取扩展点注解
//            Extension annotation = bean.getClass().getAnnotation(Extension.class);
//            // 识别身份
//            if (!identity.equals(annotation.identity())) {
//                return;
//            }
//            // 识别扩展点code、X、Y
//            matrixExtList.forEach(p -> {
//                if (p.getExtCode().equals(annotation.code()) && p.getTypeList().contains(annotation.type())) {
//                    beanList.add(bean);
//                    return;
//                }
//            });
//
//        });
        Map<String/**identity**/, List<Object>> beanMap = new HashMap<>();
        beanMap.put(identity, beanList);
        appContext.set(beanMap);
        log.info("appcontext is :{}", JSON.toJSONString(appContext));
    }
}