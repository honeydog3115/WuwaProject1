package com.sjb.wuwaechorank.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

public class SqlParameterBuilder {
    public <T> Object[] insert(T entity){
        return Arrays.stream(entity.getClass().getDeclaredFields())
                    .filter(field -> !field.getName().equals("id"))
                    .map(field -> getFieldValue(field, entity))
                    .toArray();
    }

    public <T> Object getFieldValue(Field field, T entity){
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (Exception e) {
            throw new RuntimeException(this.getClass().getName()+"의 getFieldValue()에서 에러 발생", e);
        }
    }
}
