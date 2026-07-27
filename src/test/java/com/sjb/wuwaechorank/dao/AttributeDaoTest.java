package com.sjb.wuwaechorank.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Attr;

import com.sjb.wuwaechorank.dao.attribute.AttributeDao;
import com.sjb.wuwaechorank.dto.Attribute;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class AttributeDaoTest {
    @Autowired
    AttributeDao attributeDao;

    @BeforeEach
    void setUp(){
        attributeDao.deleteAll();
    }
    
    @Test
    void add(){
        Attribute attribute = new Attribute();
        attribute.setId(0);
        attribute.setName("용융");
        attribute.setImagePath("asdf/qwer/x.jpg");
        this.attributeDao.add(attribute);

        Attribute attributeGet = this.attributeDao.get("용융");
        assertThat(attributeGet.getName()).isEqualTo("용융");
    }
}
