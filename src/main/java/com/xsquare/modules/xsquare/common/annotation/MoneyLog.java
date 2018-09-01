package com.xsquare.modules.xsquare.common.annotation;

import java.lang.annotation.*;

/**
 * @功能描述
 *  用于记录金钱方面的日志信息注解
 * @author YuSongYuan
 * @date 2018/3/24
 * @since JDK 1.8
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MoneyLog {
}
