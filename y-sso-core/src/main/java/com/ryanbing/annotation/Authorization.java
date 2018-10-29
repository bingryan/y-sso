package com.ryanbing.annotation;

import java.lang.annotation.*;

/**
 * @author ryan
 **/

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Authorization {
}
