package com.sjb.wuwaechorank.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

@SpringBootTest
public class AttributeDaoTest {
    private static final String TABLE_NAME = "attribute";

    @Autowired
    DaoJDBCUtil daoTestUtil;

    @Autowired
    AttributeDao attributeDao;

    private Attribute attribute1;
    private Attribute attribute2;
    private Attribute attribute3;

    @BeforeEach
    void setUp(){
        daoTestUtil.initTables(TABLE_NAME);
        this.attribute1 = new Attribute(1, "용융", "asdf/qwer/a.jpg");
        this.attribute2 = new Attribute(2, "회절", "asdf/qwer/b.jpg");
        this.attribute3 = new Attribute(3, "전도", "asdf/qwer/c.jpg");
    }
    
    @Test
    void add(){
        this.attributeDao.add(this.attribute1);
        Attribute attribute =  this.attributeDao.get(this.attribute1.getId());
        assertThat(attribute.getName()).isEqualTo(attribute1.getName());
    }
    @Test 
    void getAll(){
        this.attributeDao.add(this.attribute1);
        this.attributeDao.add(this.attribute2);
        this.attributeDao.add(this.attribute3);

        List<Attribute> attributes = this.attributeDao.getAll();
        assertEquals(3, attributes.size());
    }
    @Test
    void getCount(){
        this.attributeDao.add(this.attribute1);
        this.attributeDao.add(this.attribute2);
        this.attributeDao.add(this.attribute3);

        assertEquals(3, this.attributeDao.getCount());
    }
    @Test
    void delete(){
        this.attributeDao.add(this.attribute1);
        
        this.attributeDao.delete(1);
        assertEquals(0, this.attributeDao.getCount());
    }
    @Test
    void update(){
        this.attributeDao.add(this.attribute1);
        this.attributeDao.update(1, new Attribute(1, "응결", "asdf/qwer/d.jpg"));

        Attribute attribute = this.attributeDao.get(1);
        assertEquals(1, attribute.getId()); 
        assertEquals("응결", attribute.getName()); 
        assertEquals("asdf/qwer/d.jpg", attribute.getImagePath()); 
    }
    @Test
    void getName(){
        this.attributeDao.add(this.attribute1);
        String name = this.attributeDao.getName(1);
        assertEquals("용융", name);
    }
}
