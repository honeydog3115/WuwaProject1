package com.sjb.wuwaechorank.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.sonataeffect.SonataEffectDao;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.SonataEffect;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.DaoSqlErrorCode;

@SpringBootTest
public class EchoDaoTest extends BaseDaoTest {
    private static final String TABLE_NAME = "echo";
    private static final String REFERENCE_TABLE_NAME = "sonataeffect";

    @Autowired
    DaoJDBCUtil daoTestUtil;

    @Autowired
    EchoDao echoDao;

    @Autowired
    SonataEffectDao sonataEffectDao;

    Echo echo1;
    Echo echo2;
    Echo echo3;
    Echo echo4;
    SonataEffect sonataEffect1;
    SonataEffect sonataEffect2;

    @BeforeEach
    void setUp(){
        daoTestUtil.initTables(TABLE_NAME);
        daoTestUtil.initTables(REFERENCE_TABLE_NAME);
        echo1 = new Echo(1, "꾹꾹복어", 1, "1COST", "asdf/qwer/a.jpg");
        echo2 = new Echo(2, "타종거북이", 1, "4COST", "asdf/qwer/b.jpg");
        echo3 = new Echo(3, "화살곰", 1, "3COST", "asdf/qwer/c.jpg");
        echo4 = new Echo(4, "지옥불기사", 2, "4COST", "asdf/qwer/d.jpg");
        sonataEffect1 = new SonataEffect(1, "야밤의 서리", "asdf/qwer/a.jpg");
        sonataEffect2 = new SonataEffect(2, "솟구치는 용암", "asdf/qwer/b.jpg");
        this.sonataEffectDao.add(sonataEffect1);
        this.sonataEffectDao.add(sonataEffect2);
    }

    @Test
    void addAndGet(){
        this.echoDao.add(echo1);
        
        Echo echo = this.echoDao.get(1);
        assertEquals(echo1.getId(), echo.getId());
        assertEquals(echo1.getSonataEffectId(), echo.getSonataEffectId());
        assertEquals(echo1.getName(), echo.getName());
        assertEquals(echo1.getImagePath(), echo.getImagePath());
    }

    @Test
    void getAll(){
        this.echoDao.add(echo1);
        this.echoDao.add(echo2);
        this.echoDao.add(echo3);

        List<Echo> echos = this.echoDao.getAll();

        assertEquals(3, echos.size());
    }
    
    @Test
    void deleteAndCount(){
        this.echoDao.add(echo1);
        
        this.echoDao.delete(1);
        assertEquals(0, this.echoDao.getCount());
    }
    
    @Test
    void update(){
        this.echoDao.add(echo1);
        
        this.echoDao.update(1, echo2);
        Echo echo = this.echoDao.get(1);

        assertEquals(1, echo.getId());
        assertEquals(echo2.getName(), echo.getName());
        assertEquals(echo2.getSonataEffectId(), echo.getSonataEffectId());
        assertEquals(echo2.getCost(), echo.getCost());
        assertEquals(echo2.getImagePath(), echo.getImagePath());
    }
    
    @Test
    void foreignKeyConstraintFail(){
        this.echo1.setSonataEffectId(2);
        
        try {
            this.echoDao.add(echo1);            
        } catch (DataIntegrityViolationException e) {
            assertEquals("SQLIntegrityConstraintViolationException", e.getMostSpecificCause().getClass().getSimpleName());
            SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException)e.getCause();
            assertEquals(DaoSqlErrorCode.FOREIGN_KEY_CONSTRAINT_FAIL, sqlException.getErrorCode());
        }
    }

    @Test
    void cascadeDelete(){
        this.echoDao.add(echo1);
        this.sonataEffectDao.delete(1);
        assertEquals(0, this.echoDao.getCount()); 
    }

    @Test
    void getAllBySonataEffect(){
        this.echoDao.add(this.echo1);
        this.echoDao.add(this.echo2);
        this.echoDao.add(this.echo3);
        this.echoDao.add(this.echo4);
        
        List<Echo> echos = this.echoDao.getAllBySonataEffect(1);

        assertThat(echos.get(0)).usingRecursiveComparison().isEqualTo(echo1);
        assertThat(echos.get(1)).usingRecursiveComparison().isEqualTo(echo2);
        assertThat(echos.get(2)).usingRecursiveComparison().isEqualTo(echo3);
    }
}
