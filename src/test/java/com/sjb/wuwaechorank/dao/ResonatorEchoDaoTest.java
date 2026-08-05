package com.sjb.wuwaechorank.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.mainstat.MainStatDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dao.entity.sonataeffect.SonataEffectDao;
import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.MainStat;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.entity.SonataEffect;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.util.test.DaoSqlErrorCode;
import com.sjb.wuwaechorank.util.test.DaoTestUtil;
import com.sjb.wuwaechorank.util.test.TestFixture;

@SpringBootTest
public class ResonatorEchoDaoTest {
    private static final String TABLE_NAME = "resonatorecho";

    @Autowired
    DaoTestUtil daoTestUtil;

    @Autowired
    TestFixture testFixture;

    @Autowired
    ResonatorEchoDao resonatorEchoDao;

    ResonatorEcho resonatorEcho1;
    ResonatorEcho resonatorEcho2;
    ResonatorEcho resonatorEcho3;

    @BeforeEach
    void setUp(){
        testFixture.setReferenceEntity(ResonatorEcho.class);
        daoTestUtil.initTables(TABLE_NAME);
        daoTestUtil.initTableWithForeignKey(testFixture);

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
        try {
            this.resonatorEchoDao.add(resonatorEcho1);            
        } catch (DataIntegrityViolationException e) {
            assertEquals("SQLIntegrityConstraintViolationException", e.getMostSpecificCause().getClass().getSimpleName());
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException)e.getCause();
            assertEquals(DaoSqlErrorCode.FOREIGN_KEY_CONSTRAINT_FAIL, sqlException.getErrorCode());
        }
        
        resonatorEcho1.setMainStatId(2);
        try {
            this.resonatorEchoDao.add(resonatorEcho1);            
        } catch (DataIntegrityViolationException e) {
            assertEquals("SQLIntegrityConstraintViolationException", e.getMostSpecificCause().getClass().getSimpleName());
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException)e.getCause();
            assertEquals(DaoSqlErrorCode.FOREIGN_KEY_CONSTRAINT_FAIL, sqlException.getErrorCode());
        }

        resonatorEcho1.setSubStatId(2);
        try {
            this.resonatorEchoDao.add(resonatorEcho1);            
        } catch (DataIntegrityViolationException e) {
            assertEquals("SQLIntegrityConstraintViolationException", e.getMostSpecificCause().getClass().getSimpleName());
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException)e.getCause();
            assertEquals(DaoSqlErrorCode.FOREIGN_KEY_CONSTRAINT_FAIL, sqlException.getErrorCode());
        }
    }

    @Test
    void cascadeDeleteBySonataEffect(){
        this.resonatorEchoDao.add(resonatorEcho1);
        this.sonataEffectDao.delete(1);
        assertEquals(0, this.resonatorEchoDao.getCount()); 
    }
    @Test
    void cascadeDeleteByEcho(){
        this.resonatorEchoDao.add(resonatorEcho1);
        this.echoDao.delete(1);
        assertEquals(0, this.resonatorEchoDao.getCount()); 
    }
    @Test
    void cascadeDeleteByMainStat(){
        this.resonatorEchoDao.add(resonatorEcho1);
        this.mainStatDao.delete(1);
        assertEquals(0, this.resonatorEchoDao.getCount()); 
    }
    @Test
    void cascadeDeleteBySubStat(){
        this.resonatorEchoDao.add(resonatorEcho1);
        this.subStatDao.delete(1);
        assertEquals(0, this.resonatorEchoDao.getCount()); 
    }
}
