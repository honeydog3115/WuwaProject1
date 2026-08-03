package com.sjb.wuwaechorank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sjb.wuwaechorank.dao.beangenerator.DaoBeanDefinitionRegister;
import com.sjb.wuwaechorank.dao.beangenerator.DaoFactory;
import com.sjb.wuwaechorank.dao.beangenerator.DaoFactoryBean;
import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDaoCore;
import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDaoCoreJDBC;

@Configuration
public class DaoConfig {
    // @Bean
    // public AttributeDao attributeDao(DaoFactory<AttributeDao> daoFactory) {
    //     DaoFactoryBean<AttributeDao> daoFactoryBean = new DaoFactoryBean<>(daoFactory, AttributeDao.class);
    //     try {
    //         return daoFactoryBean.getObject();
    //     } catch (Exception e) {
    //         System.out.println("AttributeDao 생성실패");
    //     }
    //     return null;
    // }
}