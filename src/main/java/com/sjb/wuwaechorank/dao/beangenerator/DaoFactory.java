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
import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;

/**
 * 제네릭 T에 해당하는 타입의 DAO 반을 생성하는 factory 클래스입니다.<br>
 * <p>기본 CRUD의 역할을 하는 CrudDao와 DAO의 특수 SQL을 담당하는 DaoCore를 만들어
 * 다이나믹 프록시로 둘 사이를 조율하고 프록시를 반환합니다.<br>
 * {@code <T>}는 생성하려는 DAO 빈의 인터페이스 타입이 들어옵니다.(ex. {@link AttributeDao})
 */
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

    /** 
     * 다이나믹 프록시를 생성하는 함수입니다. 
     * <p>파라미터인 Dao의 인터페이스 타입을 통해서 CrudDao와 Dao의 구현체를 가져옵니다.
     * 가져온 구현체는 함수 이름에 따라 프록시에서 실행할 구현체를 조율해 줍니다.</p>
     * @param daoInterface {@code @DaoInterface}가 붙은 Dao의 인터페이스 타입
     * @return T 생성하려는 빈의 인터페이스 타입이 그대로 들어갑니다.
     */
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
    
    /** 
     * 엔티티 클래스를 가져오는 함수
     * <p>Dao 인터페이스 타입으로 생성하려는 Dao에 상응하는 엔티티 를래스를 가져옵니다.
     * @param daoInterface 필요한 엔티티 타입을 추론하기 위한 Dao 인터페이스 타입
     * @return {@code Class<?>} Dao에 대응하는 엔티티 클래스 타입
     */
    private Class<?> findEntityClass(Class<?> daoInterface) {
        // getGenericInterfaces() 는 인터페이스가 상속받는 인터페이스를 제네릭 타입을 유지하면서 가져온다.
        // Type 배열을 반환하는데 제네릭이 있는 인터페이스가 있다면 ParameterizedType이 일반 인터페이스는 Class<?>로 나온다.
        // 그래서 아래 코드에서 parameterizedType
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

    /**
     * DaoCore 인터페이스 타입을 찾는 함수
     * <p>필요한 DaoCore 인터페이스 타입을 찾아 반환합니다.</p>
     * @param daoInterface 필요한 DaoCore 인터페이스를 찾기위한 Dao 인터페이스 타입
     * @return {@code Class<?>} Dao에 대응하는 DaoCore 인터페이스 타입
     */
    private Class<?> findCoreInterface(Class<?> daoInterface){
        Class<?>[] interfaces = daoInterface.getInterfaces();
        Class<?> daoCoreInterface = Arrays.stream(interfaces)
                                    .filter(inteface->inteface.isAnnotationPresent(DaoCoreInterface.class))
                                    .findFirst()
                                    .orElseThrow(()-> new IllegalArgumentException("해당 인터페이스는 DaoCore가 존재하지 않습니다. 올바른 Dao 인터페이스인지 확인해주세요."));
        return daoCoreInterface;
    }

    /** 
     * CrudDao의 구현체를 생성하는 함수
     * 엔티티 클래스에 대응하는 CrudDao 구현체를 생성해 반환합니다.
     * @param entityClass CrudDao의 제네릭 타입에 들어갈 클래스 타입
     * @return {@code CrudDaoJDBC<?>} 생성된 CrudDao 구현체
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private CrudDaoJDBC<?> createCrudRepository(Class<?> entityClass) {
        return new CrudDaoJDBC(jdbcTemplate, sqlBuilder, sqlParamBuilder, entityClass);
    }
    
}
