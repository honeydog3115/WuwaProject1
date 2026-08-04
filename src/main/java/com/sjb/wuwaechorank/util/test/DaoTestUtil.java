package com.sjb.wuwaechorank.util.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.sjb.wuwaechorank.customannotation.Entity;
import com.sjb.wuwaechorank.customannotation.ForeignKey;

@Component
public class DaoTestUtil {
    private final JdbcTemplate jdbcTemplate;
    private final String ENTITY_PATH = "com.sjb.wuwaechorank.entity";
    private List<Class<?>> tables = new ArrayList<>();

    public DaoTestUtil(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        Set<BeanDefinition> beanDefs = scanner.findCandidateComponents(ENTITY_PATH);
        try {
            for (BeanDefinition beanDef : beanDefs) {
                this.tables.add(ClassUtils.forName(beanDef.getBeanClassName(), ClassUtils.getDefaultClassLoader()));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Entity의 클래스를 찾지 못 했습니다. 에러: " + e);
        } catch (LinkageError e) {
            System.out.println("클래스 로딩에 실패했습니다. 에러: " + e);
        }
    }

    public void initTable(String tableName){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        this.jdbcTemplate.execute("TRUNCATE TABLE "+ tableName);
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
    
    public void initTables(String... tableNames){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        for (String tableName : tableNames) {
            this.jdbcTemplate.execute("TRUNCATE TABLE "+ tableName);
        }
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    public void createEntity(Object entity){


    }
    public void findEntity(Class<?> entity){
        try {
            Object[] tables = Arrays.stream(entity.getFields())
                                .filter(field->field.isAnnotationPresent(ForeignKey.class))
                                .map(Field::getName)
                                .map(foreignKeyName->foreignKeyName.replaceAll("Id$", ""))
                                .map(tableName -> ClassUtils.forName(tableName, ClassUtils.getDefaultClassLoader()))
                                .toArray();
            if (tables.length != 0){
                for (Object table : tables) {
                    this.findEntity((Class<?>)table);
                }
            }
            else{
                Arrays.stream(tables).map(table->(Class<?>)table).map(table->table.getcon)
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Entity의 클래스를 찾지 못 했습니다. 에러: " + e);
        } catch (LinkageError e) {
            System.out.println("클래스 로딩에 실패했습니다. 에러: " + e);
        }
        

        for (Object field : fields){
            Field foreignKey = (Field)field;
            ClassUtils.forName(foreignKey.getName(), ) 
        }

        for (Object field : fields) {
            Field foreignKey = (Field)field;
            for (Class<?> table : tables) {
                if (foreignKey.getName().contains(table.getSimpleName())){
                    this.findEntity(table);
                }
            }
        }
    }

}
