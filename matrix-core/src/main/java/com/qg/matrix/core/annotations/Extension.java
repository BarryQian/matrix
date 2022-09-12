package com.qg.matrix.core.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Extension {
    /**
     * 业务身份
     * @return
     */
    String identity();

    /**
     * 值越小，优先级越高，只加载高优先级
     * @return
     */
    int priority() default Integer.MAX_VALUE;

    /**
     * 扩展点编码
     * @return
     */
    String code();

    /**
     * X 、 Y
     * @return
     */
    String type();

}
