package com.sjb.wuwaechorank.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.dao.sqlGenerator.SqlBuilder;
import com.sjb.wuwaechorank.dao.sqlGenerator.SqlParamBuilder;

@Component
public class DaoFactory {
    private final JdbcTemplate jdbcTemplate;
    private final SqlBuilder sqlBuilder;
    private final SqlParamBuilder sqlParamBuilder;

    public DaoFactory(JdbcTemplate jdbcTemplate, SqlBuilder sqlBuilder, SqlParamBuilder sqlParamBuilder){
        this.jdbcTemplate = jdbcTemplate;
        this.sqlBuilder = sqlBuilder;
        this.sqlParamBuilder = sqlParamBuilder;
    }

    public <T> T create(Class<T> daoInterface, Object daoCore){
        validateDaoInterface(daoInterface);

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
