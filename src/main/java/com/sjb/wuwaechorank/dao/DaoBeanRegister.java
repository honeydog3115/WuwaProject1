package com.sjb.wuwaechorank.dao;

import java.beans.Introspector;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

public class DaoBeanRegister implements ImportBeanDefinitionRegistrar {

    private static final String DAO_PACKAGE = "com.sjb.wuwaechorank.dao";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
            }
        };

        scanner.addIncludeFilter(new AnnotationTypeFilter(DaoInterface.class));
        Set<BeanDefinition> daoBeanDefs = scanner.findCandidateComponents(DAO_PACKAGE);

        for (BeanDefinition beanDefinition : daoBeanDefs) {
            String interfaceName = beanDefinition.getBeanClassName();
            if (interfaceName == null) continue;

            try {
                // 문자열이 아닌 Class 객체를 직접 로드하여 넘겨줍니다.
                Class<?> daoInterfaceClass = ClassUtils.forName(interfaceName, null);

                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DaoFactoryBean.class);
                builder.addConstructorArgReference("daoFactory");
                builder.addConstructorArgValue(daoInterfaceClass); 

                String shortName = ClassUtils.getShortName(interfaceName);
                String beanName = Introspector.decapitalize(shortName);

                registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("DAO 인터페이스 클래스를 찾을 수 없습니다: " + interfaceName, e);
            }
        }
    }
}