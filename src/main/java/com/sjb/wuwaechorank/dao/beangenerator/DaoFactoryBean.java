package com.sjb.wuwaechorank.dao.beangenerator;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;

// DaoFactoy로 Dao 빈을 생성해서 반환하는 클래스. Dao의 빈 정의를 만들 때 이 클래스를 사용함.
// 제네릭은 만들 Dao의 인터페이스 타입. ex) AttributeDao
public class DaoFactoryBean<T> implements FactoryBean<T>{
    // 실제로 Dao를 만들 DaoFactory 객체
    private final DaoFactory<T> daoFactory;
    // 만들 Dao의 타입
    private final Class<T> daoType;

    public DaoFactoryBean(DaoFactory<T> daoFactory, Class<T> daoType) {
        this.daoFactory = daoFactory;
        this.daoType = daoType;
    }

    /** 
     * Dao 빈을 만들어서 반환하는 함수.
     * @return T Dao 빈의 인터페이스 타입
     * @throws Exception
     */
    @Override
    public @Nullable T getObject() throws Exception {
        return daoFactory.create(daoType);
    }


    /** 
     * @return {@code Class<?>}
     */
    @Override
    public @Nullable Class<?> getObjectType() {
        return daoType;
    }
}
