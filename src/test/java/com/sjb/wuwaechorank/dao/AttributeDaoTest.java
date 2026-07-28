package com.sjb.wuwaechorank.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.attribute.AttributeDao;
import com.sjb.wuwaechorank.dto.Attribute;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;


@SpringBootTest
public class AttributeDaoTest {
    @Autowired
    AttributeDao attributeDao;

    private Attribute attribute1;
    private Attribute attribute2;
    private Attribute attribute3;

    @BeforeEach
    void setUp(){
        this.attributeDao.init();
        this.attribute1 = new Attribute(1, "용융", "asdf/qwer/a.jpg");
        this.attribute2 = new Attribute(2, "회절", "asdf/qwer/b.jpg");
        this.attribute3 = new Attribute(3, "전도", "asdf/qwer/c.jpg");
    }
    
    @Test
    void add(){
        this.attributeDao.add(this.attribute1);

        Attribute attributeGet = this.attributeDao.get(1);
        assertThat(attributeGet.getName()).isEqualTo("용융");
    }
    @Test
    void get(){
        this.attributeDao.add(this.attribute1);

        Attribute attribute = this.attributeDao.get(1);
        assertThat(attribute.getId()).isEqualTo(1);
        assertThat(attribute.getName()).isEqualTo("용융");
        assertThat(attribute.getImagePath()).isEqualTo("asdf/qwer/a.jpg");
    }
    @Test 
    void getAll(){
        this.attributeDao.add(this.attribute1);
        this.attributeDao.add(this.attribute2);
        this.attributeDao.add(this.attribute3);

        List<Attribute> attributes = this.attributeDao.getAll();
        assertEquals(3, attributes.size());
        assertEquals("용융", attributes.get(0).getName()); 
        assertEquals("회절", attributes.get(1).getName()); 
        assertEquals("전도", attributes.get(2).getName()); 
    }

    @Test
    void delete(){
        this.attributeDao.add(this.attribute1);
        this.attributeDao.add(this.attribute2);

        Attribute attribute = this.attributeDao.get(1);
        assertThat(attribute.getName()).isEqualTo("용융");
        
        this.attributeDao.delete(1);
        assertEquals(1, this.attributeDao.getCount());
    }
    
    @Test
    void deleteAll(){
        this.attributeDao.add(this.attribute1);
        this.attributeDao.add(this.attribute2);
        this.attributeDao.add(this.attribute3);
        
        Attribute attribute = this.attributeDao.get(1);
        assertThat(attribute.getName()).isEqualTo("용융");
        attribute = this.attributeDao.get(2);
        assertThat(attribute.getName()).isEqualTo("회절");
        attribute = this.attributeDao.get(3);
        assertThat(attribute.getName()).isEqualTo("전도");
        
        this.attributeDao.deleteAll();
        assertEquals(0, this.attributeDao.getCount());
    }

    @Test
    void update(){
        Attribute attribute4 = new Attribute(1, "인멸", "asdf/qwer/d.jpg");

        this.attributeDao.add(this.attribute1);
        this.attributeDao.update(1, attribute4);

        Attribute attribute = this.attributeDao.get(1);
        assertEquals("인멸", attribute.getName());
        assertEquals("asdf/qwer/d.jpg", attribute.getImagePath());
    }
}
