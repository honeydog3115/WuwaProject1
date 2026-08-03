package com.sjb.wuwaechorank.dao.beangenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.sjb.wuwaechorank.dao.crud.CrudDao;
import com.sjb.wuwaechorank.dao.crud.CrudDaoJDBC;

public class DaoInvocationHandler implements InvocationHandler {
    private final CrudDaoJDBC<?> crudDaoJDBC;
    private final Object daoCore;
    private final Method[] interfaceMethods;
    
    public DaoInvocationHandler(CrudDaoJDBC<?> crudDaoJDBC, Object customDao) {
        this.crudDaoJDBC = crudDaoJDBC;
        this.daoCore = customDao;
        this.interfaceMethods = CrudDao.class.getMethods();
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
        return Arrays.stream(this.interfaceMethods)
                .anyMatch(
                    interfaceMethod->interfaceMethod.getName().equals(method.getName()) && 
                    Arrays.equals(interfaceMethod.getParameterTypes(), method.getParameterTypes()));
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