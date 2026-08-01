package com.sjb.wuwaechorank.dao.sqlGenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.dao.PrimaryKey;

@Component
public class BaseSqlParamBuilder implements SqlParamBuilder {
    public Object[] insert(Object entity){
        return Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(field -> !field.isAnnotationPresent(PrimaryKey.class))
                    .map(field -> getFieldValue(field, entity))
                    .toArray();
    }

    public Object[] update(Object entity, Object primaryKey){
        // Field[] fields = entity.getClass().getDeclaredFields();
        // Stream.concat(Arrays.stream(fields,1,fields.length), Stream.of(fields[0])).toArray();
        Object[] param = Arrays.stream(entity.getClass().getDeclaredFields())
                        .filter(field -> !field.isAnnotationPresent(PrimaryKey.class))
                        .map(field -> getFieldValue(field, entity))
                        .toArray();
        param = Stream.concat(Arrays.stream(param), Stream.of(primaryKey)).toArray();

        return param;
    }

    public <T> Object getFieldValue(Field field, Object entity){
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (Exception e) {
            throw new RuntimeException(this.getClass().getName()+"의 getFieldValue()에서 에러 발생", e);
        }
    }
}
