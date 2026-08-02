// package com.sjb.wuwaechorank.dao;

// import java.beans.Introspector;
// import java.util.Arrays;
// import java.util.Set;

// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
// import org.springframework.beans.factory.config.BeanDefinition;
// import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
// import org.springframework.beans.factory.support.BeanDefinitionBuilder;
// import org.springframework.beans.factory.support.BeanDefinitionRegistry;
// import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
// import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
// import org.springframework.core.type.AnnotationMetadata;
// import org.springframework.core.type.ClassMetadata;
// import org.springframework.core.type.filter.AnnotationTypeFilter;
// import org.springframework.stereotype.Component;
// import org.springframework.util.ClassUtils;

// public class DaoBeanRegisterTmp implements BeanDefinitionRegistryPostProcessor {
//     private static final String DAO_PACKAGE = "com.sjb.wuwaechorank.dao";

//     @Override
//     public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//         ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false) {
//                 @Override
//                 protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//                     // 인터페이스이면서 독립적인 구조면 후보로 인정 (여기서 탈락을 막아줍니다!)
//                     return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
//                 }
//             };

//         scanner.addIncludeFilter(new AnnotationTypeFilter(DaoInterface.class));
//         Set<BeanDefinition> daoBeanDefs = scanner.findCandidateComponents(DAO_PACKAGE);
//         System.out.println("found beanDefs number: " + daoBeanDefs.size());
        
//         for (BeanDefinition beanDefinition : daoBeanDefs) {
//             String interfaceName = beanDefinition.getBeanClassName();
//             if (interfaceName == null) continue;
//             System.out.println("BEAN NAME : " + beanDefinition.getBeanClassName());
//             BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DaoFactoryBean.class);
//             // try {
//                 // Class<?> beanType = ClassUtils.forName(interfaceName, this.getClass().getClassLoader());
//                 builder.addConstructorArgValue(interfaceName); 
//                 // builder.addConstructorArgValue(beanType);

//                 // 2. 빈 이름 결정 및 등록
//                 String shortName = ClassUtils.getShortName(interfaceName);
//                 String beanName = Introspector.decapitalize(shortName);

//                 registry.registerBeanDefinition(beanName, builder.getBeanDefinition());



//                 // Class<?> beanType = Class.forName(beanDefinition.getBeanClassName());
                
//                 // builder.addConstructorArgReference("daoFactory");
//                 // builder.addConstructorArgValue(beanType);
//                 // registry.registerBeanDefinition(Introspector.decapitalize(beanType.getSimpleName()), builder.getBeanDefinition());
//             // } catch (ClassNotFoundException e) {
//             //     throw new IllegalStateException("DAO 클래스를 찾을 수 없습니다.", e);
//             // }
//         }
//     }
//     @Override
//     public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//         // TODO Auto-generated method stub
//         BeanDefinitionRegistryPostProcessor.super.postProcessBeanFactory(beanFactory);
//     }
// }
