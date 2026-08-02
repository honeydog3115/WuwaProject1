package com.sjb.wuwaechorank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sjb.wuwaechorank.dao.DaoBeanRegister;
import com.sjb.wuwaechorank.dao.DaoFactory;
import com.sjb.wuwaechorank.dao.DaoFactoryBean;
import com.sjb.wuwaechorank.dao.attribute.AttributeDao;
import com.sjb.wuwaechorank.dao.attribute.AttributeDaoCore;
import com.sjb.wuwaechorank.dao.attribute.AttributeDaoCoreJDBC;

@Configuration
// @Import(DaoBeanRegister.class) 
public class DaoConfig {
    @Bean
    public AttributeDao attributeDao(DaoFactory<AttributeDao> daoFactory) {
        DaoFactoryBean<AttributeDao> daoFactoryBean = new DaoFactoryBean<>(daoFactory, AttributeDao.class);
        try {
            return daoFactoryBean.getObject();
        } catch (Exception e) {
            System.out.println("AttributeDao 생성실패");
        }
        return null;
    }
}