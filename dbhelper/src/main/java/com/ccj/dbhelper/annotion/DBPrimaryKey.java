package com.ccj.dbhelper.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给主键自增长属性
 * 如果是id
 *
 * Created by ccj on 2017/1/12.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface DBPrimaryKey {

}
