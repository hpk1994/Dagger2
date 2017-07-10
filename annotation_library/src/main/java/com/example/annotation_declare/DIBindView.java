package com.example.annotation_declare;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by pingkun.huang on 2017/7/5.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface DIBindView {
    int viewId();
}
