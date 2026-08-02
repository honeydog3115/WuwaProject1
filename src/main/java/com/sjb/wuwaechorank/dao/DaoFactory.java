package com.sjb.wuwaechorank.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.dao.sqlGenerator.SqlBuilder;
import com.sjb.wuwaechorank.dao.sqlGenerator.SqlParamBuilder;

@Component
public class DaoFactory<T> {
    private final JdbcTemplate jdbcTemplate;
    private final SqlBuilder sqlBuilder;
    private final SqlParamBuilder sqlParamBuilder;
    private final ApplicationContext applicationContext;

    public DaoFactory(JdbcTemplate jdbcTemplate, SqlBuilder sqlBuilder, SqlParamBuilder sqlParamBuilder, ApplicationContext applicationContext){
        this.jdbcTemplate = jdbcTemplate;
        this.sqlBuilder = sqlBuilder;
        this.sqlParamBuilder = sqlParamBuilder;
        this.applicationContext = applicationContext;
    }

    public T create(Class<T> daoInterface){
        validateDaoInterface(daoInterface);
        Class<?> daoCoreInterface = this.findCoreInterface(daoInterface);

        Map<String,?> beans =  BeanFactoryUtils.beansOfTypeIncludingAncestors(
                                    applicationContext, // ListableBeanFactory (ApplicationContext)
                                    daoCoreInterface,   // 찾으려는 타입
                                    true,               // includeNonSingletons: 프로토타입 빈 포함 여부 (보통 true)
                                    false               // allowEagerInit: ★ 핵심! 아직 생성 안 된 FactoryBean을 강제로 초기화할 것인가? (false)
                                );
        // AttributeDaoCoreJDBC 빈은 AttributeDao의 구현체가 아니므로 Core가 들어온다.
        Object daoCore = beans.values().stream()
                        .filter(bean-> !daoInterface.isInstance(bean))
                        .findFirst()
                        .orElse(null);

        Class<?> entityClass = findEntityClass(daoInterface);
        
        CrudDaoJDBC<?> crudDaoJDBC = createCrudRepository(entityClass);
        DaoInvocationHandler handler = new DaoInvocationHandler(crudDaoJDBC, daoCore);
        Object proxy = Proxy.newProxyInstance(daoInterface.getClassLoader(), new Class<?>[]{daoInterface}, handler);
        return daoInterface.cast(proxy);
    }
    
    private Class<?> findEntityClass(Class<?> daoInterface) {
        for (Type type : daoInterface.getGenericInterfaces()) {
            if (!(type instanceof ParameterizedType parameterizedType)) {
                continue;
            }

            if (parameterizedType.getRawType() != CrudDao.class) {
                continue;
            }

            Type entityType = parameterizedType.getActualTypeArguments()[0];

            if (entityType instanceof Class<?> entityClass) {
                return entityClass;
            }

            throw new IllegalArgumentException("엔티티 타입이 구체적인 Class가 아닙니다: "+ entityType);
        }

        throw new IllegalArgumentException("CrudDao<T>를 찾을 수 없습니다"+ daoInterface.getName());
    }

    private Class<?> findCoreInterface(Class<?> daoInterface){
        Class<?>[] interfaces = daoInterface.getInterfaces();
        Class<?> daoCoreInterface = Arrays.stream(interfaces)
                                    .filter(inteface->inteface.getSimpleName().contains("Core"))
                                    .findFirst()
                                    .orElseThrow(()-> new IllegalArgumentException("해당 인터페이스는 DaoCore가 존재하지 않습니다. Dao 인터페이스인지 확인해주세요."));
        return daoCoreInterface;
    }

    private void validateDaoInterface(Class<?> daoInterface) {
        if (!daoInterface.isInterface()) {
            throw new IllegalArgumentException("DAO 타입은 인터페이스여야 합니다: " + daoInterface.getName());
        }
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CrudDaoJDBC<?> createCrudRepository(Class<?> entityClass) {
        return new CrudDaoJDBC(jdbcTemplate, sqlBuilder, sqlParamBuilder,entityClass);
    }
    
}
