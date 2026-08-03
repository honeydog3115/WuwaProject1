package com.sjb.wuwaechorank.customannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * DaoBeanDefinitionRegister에서 AttributeDao와 같은 
 * 엔티티의 Dao 인터페이스를 어노테이션 조건으로 가져오기 위해 만든 어노테이션.
 * 
 * 엔티티의 Dao 인터페이스를 만들 때 이 어노테이션을 붙여주세요.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DaoInterface {
    
}
