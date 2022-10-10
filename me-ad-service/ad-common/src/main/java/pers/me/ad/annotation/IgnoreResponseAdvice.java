package pers.me.ad.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-10
 */
@Target({ElementType.TYPE,ElementType.METHOD}) //注解作用的目标可以在类上，也可以在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {
}
