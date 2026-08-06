package com.sjb.wuwaechorank.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;

@Component
public class DaoJDBCUtil {
    private final JdbcTemplate jdbcTemplate;
    private TestFixture testFixture;

    public DaoJDBCUtil(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setTestFixture(TestFixture testFixture){
        this.testFixture = testFixture;
    }
    
    public void initTables(String... tableNames){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        for (String tableName : tableNames) {
            this.jdbcTemplate.execute("TRUNCATE TABLE "+ tableName);
        }
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
    
    public void initReferenceTables(){
        Map<Object, Object> refEntityAndDao = testFixture.getReferenceEntityAndDaoMap();
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        for (Object entity : refEntityAndDao.keySet()) {
            this.jdbcTemplate.execute("TRUNCATE TABLE "+ entity.getClass().getSimpleName());
            this.daoMethodInvoke(entity, "add" , entity);
        } 
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    public void deleteRefEntity(Class<?> entityClass){
        Object key = this.testFixture.refEntityAndDao.keySet().stream()
                    .filter(refentity->refentity.getClass().getSimpleName().equals(entityClass.getSimpleName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("deleteEntity()의 매개변수로 잘못된 클래스를 전달받았습니다."));
        
        Object primaryKey = Arrays.stream(key.getClass().getDeclaredFields())
                            .filter(field->field.isAnnotationPresent(PrimaryKey.class))
                            .map(field->this.fieldGet(field, key))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("PrimaryKey 어노테이션이 존재하지 않습니다. " + key.getClass().getSimpleName() + " 엔티티를 확인해 주세요."));
        this.daoMethodInvoke(key, "delete", primaryKey);
    }
    
    public Object fieldGet(Field field, Object key){
        try {
            field.setAccessible(true);
            Object primaryKey =  field.get(key);
            return primaryKey;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("필드에 접근할 수 없습니다.", e);
        }
    }

    public void daoMethodInvoke(Object key, String methodName, Object... param){
        Object dao = this.testFixture.refEntityAndDao.get(key);
        try{
            Method daoMethod = dao.getClass().getMethod(methodName,Object.class);
            daoMethod.invoke(dao, param);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(dao.getClass().getSimpleName() + "는 "+ methodName + "()가 존재하지 않습니다.", e);
        } catch (IllegalAccessException e){
            throw new IllegalStateException(dao.getClass().getSimpleName() + "의 "+ methodName + "()에 대한 접근이 잘 못되었습니다.", e);
        } catch (InvocationTargetException e){
            throw new IllegalCallerException(dao.getClass().getSimpleName() + "의 "+ methodName + "()를 실행 중 내부에서 예외가 발생했습니다.", e);
        }
    }
}