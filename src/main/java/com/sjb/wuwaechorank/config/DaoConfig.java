package com.sjb.wuwaechorank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sjb.wuwaechorank.dao.DaoFactory;
import com.sjb.wuwaechorank.dao.attribute.AttributeDao;
import com.sjb.wuwaechorank.dao.attribute.AttributeDaoCore;

@Configuration
public class DaoConfig {
    @Bean
    public AttributeDao attributeDao(DaoFactory daoFactory, AttributeDaoCore attributeDaoCore) {
        return daoFactory.create(AttributeDao.class, attributeDaoCore);
    }
}
