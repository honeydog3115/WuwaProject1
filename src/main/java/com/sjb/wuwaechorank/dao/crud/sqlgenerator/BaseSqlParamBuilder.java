package com.sjb.wuwaechorank.dao.crud.sqlgenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;

@Component("sqlParamBuilder")
public class BaseSqlParamBuilder implements SqlParamBuilder {
    /** 
     * @param entity
     * @return Object[]
     */
    public Map<String, Object> insert(Object entity){
        Map<String, Object> param = new HashMap<>();
        Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(field -> !field.isAnnotationPresent(PrimaryKey.class))
                    .forEach(field -> param.put(field.getName(), this.getFieldValue(field, entity)));
                    System.out.println(param);
        return param;
    }

    /** 
     * @param entity
     * @param primaryKey
     * @return Object[]
     */
    public Object[] update(Object entity, Object primaryKey){
        Object[] param = Arrays.stream(entity.getClass().getDeclaredFields())
                        .filter(field -> !field.isAnnotationPresent(PrimaryKey.class))
                        .map(field -> getFieldValue(field, entity))
                        .toArray();
        param = Stream.concat(Arrays.stream(param), Stream.of(primaryKey)).toArray();

        return param;
    }

    /** 
     * @param field
     * @param entity
     * @return Object
     */
    public <T> Object getFieldValue(Field field, Object entity){
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (Exception e) {
            throw new RuntimeException(this.getClass().getName()+"의 getFieldValue()에서 에러 발생." + entity.getClass().getSimpleName() + " 엔티티의 " + field.getName() + " 필드 처리 중 발생", e);
        }
    }
}
