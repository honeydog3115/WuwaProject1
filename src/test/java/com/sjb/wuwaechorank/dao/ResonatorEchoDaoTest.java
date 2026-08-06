package com.sjb.wuwaechorank.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.MainStat;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.entity.SonataEffect;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.util.DaoSqlErrorCode;
import com.sjb.wuwaechorank.util.DaoTestUtil;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class ResonatorEchoDaoTest {
    private static final String TABLE_NAME = "resonatorecho";

    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    TestFixture testFixture;

    @Autowired
    ResonatorEchoDao resonatorEchoDao;

    ResonatorEcho resonatorEcho1;
    ResonatorEcho resonatorEcho2;
    ResonatorEcho resonatorEcho3;

    @BeforeEach
    void setUp(){
        testFixture.createReferenceEntity(ResonatorEcho.class);
        daoJDBCUtil.setTestFixture(testFixture);
        daoJDBCUtil.initTables(TABLE_NAME);
        daoJDBCUtil.initReferenceTables();

        this.resonatorEcho1 = new ResonatorEcho(1, 1, 1, 1, 50);
        this.resonatorEcho2 = new ResonatorEcho(2, 1, 1, 1, 50);
        this.resonatorEcho3 = new ResonatorEcho(3, 1, 1, 1, 50);
    }

    @Test
    void addAndGet(){
        this.resonatorEchoDao.add(resonatorEcho1);
        ResonatorEcho resonatorEcho = this.resonatorEchoDao.get(1);
        assertEquals(resonatorEcho1.getId(), resonatorEcho.getId());
        assertEquals(resonatorEcho1.getEchoId(), resonatorEcho.getEchoId());
        assertEquals(resonatorEcho1.getMainStatId(), resonatorEcho.getMainStatId());
        assertEquals(resonatorEcho1.getSubStatId(), resonatorEcho.getSubStatId());
        assertEquals(resonatorEcho1.getScore(), resonatorEcho.getScore());
    }
    
    @Test
    void getAll(){
        this.resonatorEchoDao.add(resonatorEcho1);
        this.resonatorEchoDao.add(resonatorEcho2);
        this.resonatorEchoDao.add(resonatorEcho3);

        List<ResonatorEcho> resonatorEchos = this.resonatorEchoDao.getAll();
        assertEquals(3, resonatorEchos.size());
    }

    @Test
    void deleteAndGetCount(){
        this.resonatorEchoDao.add(resonatorEcho1);
        this.resonatorEchoDao.delete(1);
        assertEquals( 0, resonatorEchoDao.getCount());
    }
    
    @Test
    void update(){
        this.resonatorEchoDao.add(resonatorEcho1);
        
        ResonatorEcho resonatorEcho = new ResonatorEcho(1, 1, 1, 1, 20);
        this.resonatorEchoDao.update(1, resonatorEcho);
        ResonatorEcho updatedResonatorEcho = this.resonatorEchoDao.get(1);

        assertEquals(resonatorEcho.getId(), updatedResonatorEcho.getId());
        assertEquals(resonatorEcho.getEchoId(), updatedResonatorEcho.getEchoId());
        assertEquals(resonatorEcho.getMainStatId(), updatedResonatorEcho.getMainStatId());
        assertEquals(resonatorEcho.getSubStatId(), updatedResonatorEcho.getSubStatId());
        assertEquals(resonatorEcho.getScore(), updatedResonatorEcho.getScore());        
    }

    @Test
    void foreignKeyConstraintFail(){
        resonatorEcho1.setEchoId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.resonatorEchoDao.add(resonatorEcho1));
        
        resonatorEcho1.setMainStatId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.resonatorEchoDao.add(resonatorEcho1));
        
        resonatorEcho1.setSubStatId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.resonatorEchoDao.add(resonatorEcho1));
    }

    @ParameterizedTest(name = "{0} 삭제시 Cascade 삭제 검증")
    @ValueSource(classes = {
        SonataEffect.class,
        Echo.class,
        MainStat.class,
        SubStat.class
    })
    void cascadeDeleteByRefEntity(Class<?> refEntityClass){
        this.resonatorEchoDao.add(resonatorEcho1);
        daoJDBCUtil.deleteRefEntity(refEntityClass);
        assertEquals(0, this.resonatorEchoDao.getCount());
    }
}
