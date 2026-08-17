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

import com.sjb.wuwaechorank.dao.entity.validstat.ValidStatDao;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.DaoTestUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class ValidStatDaoTest extends BaseDaoTest{
    private final static String TABLE_NAME = "validstat";

    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    ValidStatDao validStatDao;
    
    @Autowired
    TestFixture testFixture;

    ValidStat validStat1;
    ValidStat validStat2;
    ValidStat validStat3;

    @BeforeEach
    void setUp(){
        testFixture.createReferenceEntity(ValidStat.class);
        daoJDBCUtil.initTables(TABLE_NAME);
        daoJDBCUtil.setTestFixture(testFixture);
        daoJDBCUtil.initReferenceTables();

        validStat1 = new ValidStat(1, 1, 1, 1, 1, 1, 1);
        validStat2 = new ValidStat(2, 1, 1, 1, 1, 1, 1);
        validStat3 = new ValidStat(3, 1, 1, 1, 1, 1, 1);
    }

    @Test
    void addAndGet(){
        this.validStatDao.add(validStat1);
        ValidStat validStat = this.validStatDao.get(1);

        assertEquals(validStat1.getId(), validStat.getId());
        assertEquals(validStat1.getSubStatId1(), validStat.getSubStatId1());
        assertEquals(validStat1.getSubStatId2(), validStat.getSubStatId2());
        assertEquals(validStat1.getSubStatId3(), validStat.getSubStatId3());
        assertEquals(validStat1.getSubStatId4(), validStat.getSubStatId4());
        assertEquals(validStat1.getSubStatId5(), validStat.getSubStatId5());
    }

    @Test
    void getAll(){
        this.validStatDao.add(validStat1);
        this.validStatDao.add(validStat2);
        this.validStatDao.add(validStat3);

        List<ValidStat> validStats = this.validStatDao.getAll();

        assertEquals(3, validStats.size());
    }

    @Test
    void deleteAndGetCount(){
        this.validStatDao.add(validStat1);
        this.validStatDao.delete(1);

        assertEquals(0, this.validStatDao.getCount());
    }

    @Test
    void foreignKeyConstraintViolation(){
        this.validStat1.setSubStatId1(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.validStatDao.add(validStat1));
    }

    @ParameterizedTest
    @ValueSource(classes = {SubStat.class})
    void onDeleteSetNull(Class<?> refEntityClass) {
        this.validStatDao.add(validStat1);
        this.daoJDBCUtil.deleteRefEntity(refEntityClass);
        ValidStat validStat = this.validStatDao.get(1);
        assertThat(validStat.getSubStatId1()).isEqualTo(null);
    }
}  
