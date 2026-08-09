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

import com.sjb.wuwaechorank.customannotation.DaoInterface;

// Dao 빈들의 빈 생성 정의를 만드는 클래스
@Component
public class DaoBeanDefinitionRegister implements BeanDefinitionRegistryPostProcessor {
    private static final String DAO_PACKAGE = "com.sjb.wuwaechorank.dao";

    /** 
     * Dao 빈 정의를 만들어서 등록하는 함수.
     * @param registry 빈 정의를 등록해주는 객체
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 빈으로 만들 Dao의 인터페이스 타입을 찾기위한 스캐서 정의. 찾는 기준은 인터페이스이면서 @DaoInterface 를 달고 있을것.
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
            }
        };

        scanner.addIncludeFilter(new AnnotationTypeFilter(DaoInterface.class));
        Set<BeanDefinition> daoBeanDefs = scanner.findCandidateComponents(DAO_PACKAGE);

        // 스캐너로 발견한 인터페이스들의 빈 정의를 가져와 빈 생성법을 명시
        // 빈 생성자로 DaoFactoryBean 클래스를 지정. 그러면 빈 생성시 자동으로 해당 팩토리 클래스의 getObject를 실행함.
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