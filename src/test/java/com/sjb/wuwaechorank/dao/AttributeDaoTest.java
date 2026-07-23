package com.sjb.wuwaechorank.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dto.Attribute;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AttributeDaoTest {
    @Autowired
    AttributeDao attributeDao;
    
    @Test
    void add(){
        this.attributeDao.add();
        Attribute attribute = this.attributeDao.get();
        assertThat(attribute.getName()).isEqualTo("용융");

    }
}
