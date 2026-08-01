// package com.sjb.wuwaechorank.dao;


// import org.jspecify.annotations.Nullable;
// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.config.BeanPostProcessor;
// import org.springframework.cglib.proxy.Proxy;

// public class DaoBeanPostProcessor implements BeanPostProcessor {
//     @Override
//     public @Nullable Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//         Class<?> beanClass = bean.getClass();
//         for (Class<?> iface : beanClass.getInterfaces()) {
//             DaoInvocationHandler daoInvocationHandler = new DaoInvocationHandler();
            
//             if (iface.isAnnotationPresent(DaoInterface.class)){
//                 return Proxy.newProxyInstance(iface.getClassLoader(), new Class<?>[] {iface}, daoInvocationHandler);
//             }
//         }

//         return bean;
//     }
// }
