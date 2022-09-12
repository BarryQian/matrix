package com.qg.matrix.core.aspect;

import com.alibaba.fastjson.JSON;
import com.qg.matrix.core.interfaces.AbstractExt;
import com.qg.matrix.core.model.BaseResultModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Aspect
@Component
@Slf4j
public class AbilityExtAnnotation {

    @Around("@annotation(com.qg.matrix.core.annotations.AbilityExt)")
    public void abilityExtAround(ProceedingJoinPoint point) {

        try {
            BaseResultModel result = (BaseResultModel)point.proceed();
            AbstractExt.resultContext.set(result);
        } catch (Throwable t) {
            log.error("abilityExtAround param:{}, exception:", JSON.toJSONString(point.getArgs()), t);
        }

    }
}
