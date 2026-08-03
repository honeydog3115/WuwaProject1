package com.sjb.wuwaechorank.dao.beangenerator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.customannotation.DaoCoreInterface;
import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.dao.crud.CrudDaoJDBC;
import com.sjb.wuwaechorank.dao.crud.sqlgenerator.SqlBuilder;
import com.sjb.wuwaechorank.dao.crud.sqlgenerator.SqlParamBuilder;

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
        if (!daoInterface.isInterface()) {
            throw new IllegalArgumentException("DAO 타입은 인터페이스여야 합니다: " + daoInterface.getName());
        }
        
        // CrudDao를 가져옴
        Class<?> entityClass = findEntityClass(daoInterface);
        CrudDaoJDBC<?> crudDaoJDBC = createCrudRepository(entityClass);
        
        // 엔티티 DaoCore를 가져옴
        Class<?> daoCoreInterface = this.findCoreInterface(daoInterface);
        ObjectProvider<?> daoCoreProvider = applicationContext.getBeanProvider(daoCoreInterface);
        
        Object daoCore = daoCoreProvider.stream()
                        .filter(bean->daoCoreInterface.isInstance(bean))
                        .filter(bean->!daoInterface.isInstance(bean))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException(daoCoreInterface.getSimpleName() + "의 구현체가 존재하지 않습니다."));
        
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
                                    .filter(inteface->inteface.isAnnotationPresent(DaoCoreInterface.class))
                                    .findFirst()
                                    .orElseThrow(()-> new IllegalArgumentException("해당 인터페이스는 DaoCore가 존재하지 않습니다. 올바른 Dao 인터페이스인지 확인해주세요."));
        return daoCoreInterface;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CrudDaoJDBC<?> createCrudRepository(Class<?> entityClass) {
        return new CrudDaoJDBC(jdbcTemplate, sqlBuilder, sqlParamBuilder,entityClass);
    }
    
}
