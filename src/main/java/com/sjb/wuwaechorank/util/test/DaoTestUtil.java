package com.sjb.wuwaechorank.util.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.sjb.wuwaechorank.customannotation.Entity;
import com.sjb.wuwaechorank.customannotation.ForeignKey;

@Component
public class DaoTestUtil {
    private final JdbcTemplate jdbcTemplate;
    private TestFixture testFixture;

    public DaoTestUtil(JdbcTemplate jdbcTemplate, TestFixture testFixture){
        this.jdbcTemplate = jdbcTemplate;
        this.testFixture = testFixture;
    }
    
    public void initTables(String... tableNames){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        for (String tableName : tableNames) {
            this.jdbcTemplate.execute("TRUNCATE TABLE "+ tableName);
        }
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
    
    public void initTableWithForeignKey(TestFixture testFixture){
        Map<Object, Object> refEntityAndDao = testFixture.getReferenceEntityAndDaoMap();
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        for (Object entity : refEntityAndDao.keySet()) {
            this.jdbcTemplate.execute("TRUNCATE TABLE "+ entity.getClass().getSimpleName());
            Object dao = refEntityAndDao.get(entity);
            try {
                Method daoAdd = dao.getClass().getMethod("add",Object.class);
                daoAdd.invoke(dao, entity);
            } catch (NoSuchMethodException e) {
                System.out.println(dao.getClass().getSimpleName() + "는 add()가 존재하지 않습니다." + e);
            } catch (IllegalAccessException e){
                System.out.println(dao.getClass().getSimpleName() + "의 add()에 대한 접근이 잘 못되었습니다." + e);
            } catch (InvocationTargetException e){
                System.out.println(dao.getClass().getSimpleName() + "의 add()를 실행 중 내부에서 예외가 발생했습니다." + e);
            }
        } 
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    public void addEntityTable(TestFixture testFixture){

    }
}