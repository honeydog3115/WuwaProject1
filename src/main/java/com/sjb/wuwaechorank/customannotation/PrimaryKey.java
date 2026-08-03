package com.sjb.wuwaechorank.customannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 엔티티의 PrimaryKey로 조회, 삭제, 업데이트 등의 SQL을 만들때,
 * PrimaryKey를 참고하기 위해 만들었습니다.
 * 
 * 엔티티의 PrimaryKey에 해당하는 필드에 붙여주세요.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    
}
