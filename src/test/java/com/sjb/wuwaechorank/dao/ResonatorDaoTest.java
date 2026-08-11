package com.sjb.wuwaechorank.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Resonator;
import com.sjb.wuwaechorank.entity.Weapon;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.DaoTestUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class ResonatorDaoTest {
    private static final String TABLE_NAME = "resonator";
    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    ResonatorDao resonatorDao;

    @Autowired
    TestFixture testFixture;

    Resonator resonator1;
    Resonator resonator2;
    Resonator resonator3;

    @BeforeEach
    void setUp(){
        daoJDBCUtil.initTables(TABLE_NAME);
        testFixture.createReferenceEntity(Resonator.class);
        daoJDBCUtil.setTestFixture(testFixture);
        daoJDBCUtil.initReferenceTables();

        this.resonator1 = new Resonator(1, "카르티시아", 1, 1, 5, 1, "120%","asdf/qwer/a.jpg");
        this.resonator2 = new Resonator(2, "에이메스", 1, 1, 5,1, "125%", "asdf/qwer/b.jpg");
        this.resonator3 = new Resonator(3, "유노", 1, 1, 5, 1, "130%", "asdf/qwer/c.jpg");
    }
    
    @Test
    void addAndGet(){
        this.resonatorDao.add(this.resonator1);
        Resonator resonator = this.resonatorDao.get(1);
        assertEquals(resonator1.getId(), resonator.getId());
        assertEquals(resonator1.getName(), resonator.getName());
        assertEquals(resonator1.getAttributeId(), resonator.getAttributeId());
        assertEquals(resonator1.getWeaponId(), resonator.getWeaponId());
        assertEquals(resonator1.getImagePath(), resonator.getImagePath());
    }
    
    @Test
    void getAll(){
        this.resonatorDao.add(this.resonator1);
        this.resonatorDao.add(this.resonator2);
        this.resonatorDao.add(this.resonator3);
        
        List<Resonator> resonators = this.resonatorDao.getAll();
        assertEquals(3, resonators.size());
    }

    @Test
    void delete(){
        this.resonatorDao.add(resonator1);
        this.resonatorDao.delete(1);
        assertEquals(0, this.resonatorDao.getCount()); 
    }

    @Test
    void update(){
        this.resonatorDao.add(resonator1);
        Resonator resonator = new Resonator(1, "양양", 1, 1, 4, 1, "115%", "asdf/qwer/d.jpg");
        this.resonatorDao.update(1, resonator);
        Resonator updatedResonator = this.resonatorDao.get(1);
        assertThat(updatedResonator).usingRecursiveComparison().isEqualTo(resonator);
    }

    @Test
    void foreignKeyConstraintViolation(){
        this.resonator1.setAttributeId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.resonatorDao.add(resonator1));
        this.resonator1.setWeaponId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.resonatorDao.add(resonator1));
    }

    @ParameterizedTest(name="{0} 삭제시 연쇄 삭제 테스트")
    @ValueSource(classes={
        Attribute.class,
        Weapon.class
    })
    void cascadeDelete(Class<?> refEntityClass){
        this.resonatorDao.add(resonator1);
        this.daoJDBCUtil.deleteRefEntity(refEntityClass);
        assertEquals(0, this.resonatorDao.getCount());
    }
}
