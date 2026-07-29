package com.sjb.wuwaechorank.dao;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SqlBuilder {
    public <T> String insert(T entity){
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        String columns = Arrays.stream(fields)
                        .map(Field::getName)
                        // 변수의 이름이 id인 것을 제외
                        .filter(name -> !name.equals("id"))
                        .collect(Collectors.joining(","));

        String[] array = new String[fields.length];
        Arrays.fill(array, "?");
        String parameter = String.join(",", array);
            
        String sql = "INSERT INTO "+ clazz.getSimpleName() +"("+ columns +") VALUES(" + parameter +")";
        return sql;
    }

    public <T> String select(T entity){
        Class<?> clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        String column = Arrays.stream(fields)
                        .map(Field::getName)
                        .filter(name -> name.equals("id"))
                        .collect(Collectors.joining());

        String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE " + column + "=" + "?";
        return sql;
    }

    public <T> String selectAll(T entity){
        Class<?> clazz = entity.getClass();
        String sql = "SELECT * FROM " + clazz.getSimpleName();
        return sql;
    }
}
