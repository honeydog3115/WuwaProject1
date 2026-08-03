package com.sjb.wuwaechorank.dao.beangenerator;

import java.beans.Introspector;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.sjb.wuwaechorank.customannotation.DaoInterface;

@Component
public class DaoBeanDefinitionRegister implements BeanDefinitionRegistryPostProcessor {
    private static final String DAO_PACKAGE = "com.sjb.wuwaechorank.dao";

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
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
                Class<?> daoInterfaceClass = Class.forName(interfaceName);

                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DaoFactoryBean.class);
                builder.addConstructorArgReference("daoFactory");
                builder.addConstructorArgValue(daoInterfaceClass); 

                String beanName = Introspector.decapitalize(daoInterfaceClass.getSimpleName());

                registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("DAO 인터페이스 클래스를 찾을 수 없습니다: " + interfaceName, e);
            }
        }
        
    }
}