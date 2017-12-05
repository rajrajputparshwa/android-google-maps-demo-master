package com.example.mapdemo;

/**
 * Created by admin on 12/5/2017.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface NeedsPermission {
    String[] value();

    int maxSdkVersion() default 0;
}
