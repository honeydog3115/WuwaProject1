package com.sjb.wuwaechorank.dao.crud.sqlgenerator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;

@Component("sqlBuilder")
public class BaseSqlBuilder implements SqlBuilder {
    /** 
     * @param clazz
     * @return String
     */
    public String insert(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        
        String columns = Arrays.stream(fields)
                        .filter(field -> !field.isAnnotationPresent(PrimaryKey.class))
                        .map(Field::getName)
                        // 변수의 이름이 id인 것을 제외
                        .collect(Collectors.joining(","));

        String[] array = new String[fields.length-1];
        Arrays.fill(array, "?");
        String parameter = String.join(",", array);
            
        String sql = "INSERT INTO "+ clazz.getSimpleName() +"("+ columns +") VALUES(" + parameter +")";
        return sql;
    }

    /** 
     * @param clazz
     * @return String
     */
    public String select(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        
        String column = Arrays.stream(fields)
        .filter(filed -> filed.isAnnotationPresent(PrimaryKey.class))
        .map(Field::getName)
        .collect(Collectors.joining());
        
        String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE " + column + "=" + "?";
        return sql;
    }
    
    /** 
     * @param clazz
     * @return String
     */
    public String selectAll(Class<?> clazz){
        String sql = "SELECT * FROM " + clazz.getSimpleName();
        return sql;
    }

    /** 
     * @param clazz
     * @return String
     */
    @Override
    public String delete(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String column = Arrays.stream(fields)
                        .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                        .map(Field::getName)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Primary 키가 테이블에 존재하지 않습니다."));

        String sql = "DELETE FROM " + clazz.getSimpleName() + " WHERE " + column + "=?";
        return sql;
    }
    /** 
     * @param clazz
     * @return String
     */
    @Override
    public String count(Class<?> clazz) {
        return new String("SELECT COUNT(*) FROM " + clazz.getSimpleName());
    }
    /** 
     * @param clazz
     * @return String
     */
    @Override
    public String update(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        
        String columns = Arrays.stream(fields)
                        .filter(field -> !field.isAnnotationPresent(PrimaryKey.class))
                        .map(Field::getName)
                        .collect(Collectors.joining("=?,"));
        String primaryKey = Arrays.stream(fields)
                            .filter(field -> field.isAnnotationPresent(PrimaryKey.class))
                            .map(Field::getName)
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("PrimaryKey가 존재하지 않는 테이블입니다."));
        
        columns = columns.concat("=?");
        String sql = "UPDATE " + clazz.getSimpleName() + " SET " + columns + " WHERE " + primaryKey + "=?"; 
        return sql;
    }
}
