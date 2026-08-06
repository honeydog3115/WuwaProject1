package com.sjb.wuwaechorank.util;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.resource.beans.container.internal.NoSuchBeanException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.sjb.wuwaechorank.customannotation.DaoInterface;
import com.sjb.wuwaechorank.customannotation.ForeignKey;

@Component
public class TestFixture {
    private final ApplicationContext applicationContext;
    // 키에는 엔티티 객체가 값에는 엔티티의 Dao의 객체가 담깁니다.
    Map<Object,Object> refEntityAndDao = new HashMap<>();
    private final String ENTITY_PATH = "com.sjb.wuwaechorank.entity";

    public TestFixture(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    public Map<Object,Object> getReferenceEntityAndDaoMap(){
        return this.refEntityAndDao;
    }

    public void createReferenceEntity(Class<?> entityClass){
        List<Class<?>> refTableList = new ArrayList<>();
        findReferenceTable(entityClass, refTableList);
        Map<String, Object> daos = applicationContext.getBeansWithAnnotation(DaoInterface.class);
        for (Class<?> refTable : refTableList) {
            try {
                Method builderMethod = refTable.getDeclaredMethod("builder");
                Object builder = builderMethod.invoke(null);
                Method build = builder.getClass().getDeclaredMethod("build");
                Object entity = build.invoke(builder);

                String daoBeanName = Introspector.decapitalize(refTable.getSimpleName()+"Dao");
                Object targetDao = daos.get(daoBeanName);

                if (targetDao==null){
                    throw new IllegalArgumentException("DAO 빈을 찾을 수 없습니다: " + daoBeanName);
                };
                this.refEntityAndDao.put(entity, targetDao);

            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(refTable.getSimpleName() + "의 builder/build를 찾지 못했습니다." + e);
            } catch (IllegalAccessException e){
                throw new IllegalStateException(refTable.getSimpleName() + "의 builder메서드에 접근하지 못 했습니다." + e);
            } catch (InvocationTargetException e){
                throw new IllegalStateException(refTable.getSimpleName() + "의 builder메서드 실행 중 예기치 못한 에외가 발생했습니다." + e.getClass());
            }
        }
    }

    public void findReferenceTable(Class<?> entityClass, List<Class<?>> tableList){
        List<Class<?>> refEntityClasses = Arrays.stream(entityClass.getDeclaredFields())
                            .filter(field->field.isAnnotationPresent(ForeignKey.class))
                            .map(Field::getName)
                            .map(foreignKeyName->foreignKeyName.replaceAll("Id$", ""))
                            .map(StringUtils::capitalize)
                            .map(this::findClassByName)
                            .collect(Collectors.toList());

        // 순환 참조 방지로 AI가 도와줌
        for (Class<?> refEntity : refEntityClasses) {                    
            if(!tableList.contains(refEntity) ){
                tableList.add(refEntity);
                this.findReferenceTable(refEntity, tableList);
            }
        }
    }

    public Class<?> findClassByName(String tableName){
        String path = ENTITY_PATH + "." + tableName;
        try {
            return ClassUtils.forName(path, ClassUtils.getDefaultClassLoader());
        } catch (ClassNotFoundException |LinkageError e) {
            throw new IllegalArgumentException("Entity 클래스 로딩에 실패앴습니다." + path, e);
        }
    }

}
