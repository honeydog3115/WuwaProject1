package com.sjb.wuwaechorank.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.cglib.proxy.InvocationHandler;

public class DaoInvocationHandler implements InvocationHandler {
    private final CrudDaoJDBC<?> crudDaoJDBC;
    private final Object daoCore;
    
    public DaoInvocationHandler(CrudDaoJDBC<?> crudDaoJDBC, Object customDao) {
        this.crudDaoJDBC = crudDaoJDBC;
        this.daoCore = customDao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return invokeObjectMethod(proxy, method, args);
        }
        Object target = isBaseDaoMethod(method)?crudDaoJDBC:daoCore;
        Method targetMethod = target.getClass().getMethod(method.getName(),method.getParameterTypes());

        try {
            return targetMethod.invoke(target, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    private boolean isBaseDaoMethod(Method method) {
        try {
            CrudDao.class.getMethod(method.getName(),method.getParameterTypes());
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private Object invokeObjectMethod(Object proxy,Method method,Object[] args) {
        return switch (method.getName()) {
            case "toString" ->"DAO Proxy[" + daoCore.getClass().getSimpleName() + "]";

            case "hashCode" ->System.identityHashCode(proxy);

            case "equals" ->proxy == args[0];

            default ->throw new UnsupportedOperationException(method.getName());
        };
    }
}