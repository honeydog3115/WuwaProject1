package com.sjb.wuwaechorank.customannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DaoFactory에서 AttributeDaoCore와 같은 엔티티의 DaoCore 인터페이스를 
 * 찾을 때 사용되는 어노테이션.
 * 
 * 엔티티의 DaoCore 인터페이스를 만들 때 위에 붙여주세요.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoCoreInterface {
}