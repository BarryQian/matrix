package com.qg.matrix.core.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
    /**
     * bean name
     * @return
     */
    String name();
}
