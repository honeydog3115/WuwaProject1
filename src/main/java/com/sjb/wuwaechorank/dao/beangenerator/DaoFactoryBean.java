package com.sjb.wuwaechorank.dao.beangenerator;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.FactoryBean;

public class DaoFactoryBean<T> implements FactoryBean<T>{
    private final DaoFactory<T> daoFactory;
    private final Class<T> daoType;

    public DaoFactoryBean(DaoFactory<T> daoFactory, Class<T> daoType) {
        this.daoFactory = daoFactory;
        this.daoType = daoType;
    }

    /** 
     * @return T
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
