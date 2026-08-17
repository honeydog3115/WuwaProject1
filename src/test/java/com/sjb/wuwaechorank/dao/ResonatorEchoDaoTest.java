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
import org.springframework.jdbc.core.JdbcTemplate;

import com.sjb.wuwaechorank.dao.entity.presetecho.PresetEchoDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.PresetEcho;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.entity.SonataEffect;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.DaoTestUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class ResonatorEchoDaoTest extends BaseDaoTest{
    private static final String TABLE_NAME = "resonatorecho";
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    TestFixture testFixture;

    @Autowired
    ResonatorEchoDao resonatorEchoDao;

    @Autowired
    PresetEchoDao presetEchoDao;

    ResonatorEcho resonatorEcho1;
    ResonatorEcho resonatorEcho2;
    ResonatorEcho resonatorEcho3;

    PresetEcho presetEcho1;
    PresetEcho presetEcho2;
    PresetEcho presetEcho3;

    @BeforeEach
    void setUp(){
        testFixture.createReferenceEntity(ResonatorEcho.class);
        daoJDBCUtil.setTestFixture(testFixture);
        daoJDBCUtil.initTables(TABLE_NAME);
        daoJDBCUtil.initTables("presetecho");
        daoJDBCUtil.initReferenceTables();

        this.resonatorEcho1 = new ResonatorEcho(1, 1, 1, 50);
        this.resonatorEcho2 = new ResonatorEcho(2, 1, 1, 50);
        this.resonatorEcho3 = new ResonatorEcho(3, 1, 1, 50);

        this.presetEcho1 = new PresetEcho(1, 1, 1);
        this.presetEcho2 = new PresetEcho(2, 1, 2);
        this.presetEcho3 = new PresetEcho(3, 1, 3);
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        this.presetEchoDao.add(presetEcho1);
        this.presetEchoDao.add(presetEcho2);
        this.presetEchoDao.add(presetEcho3);
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    @Test
    void addAndGet(){
        this.resonatorEchoDao.add(resonatorEcho1);
        ResonatorEcho resonatorEcho = this.resonatorEchoDao.get(1);
        assertThat(resonatorEcho).usingRecursiveComparison().isEqualTo(resonatorEcho1);
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
        
        ResonatorEcho resonatorEcho = new ResonatorEcho(1, 1, 1, 20);
        this.resonatorEchoDao.update(1, resonatorEcho);
        ResonatorEcho updatedResonatorEcho = this.resonatorEchoDao.get(1);

        assertThat(updatedResonatorEcho).usingRecursiveComparison().isEqualTo(resonatorEcho);
    }

    @Test
    void foreignKeyConstraintFail(){
        resonatorEcho1.setEchoId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.resonatorEchoDao.add(resonatorEcho1));
        
        resonatorEcho1.setMainStatId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.resonatorEchoDao.add(resonatorEcho1));   
    }
    @ParameterizedTest(name = "{0} 삭제시 Cascade 삭제 검증")
    @ValueSource(classes = {
        SonataEffect.class,
        Echo.class
    })
    void cascadeDeleteByRefEntity(Class<?> refEntityClass){
        this.resonatorEchoDao.add(resonatorEcho1);
        daoJDBCUtil.deleteRefEntity(refEntityClass);
        assertEquals(0, this.resonatorEchoDao.getCount());
    }

    @Test
    void getAllByPresetId(){
        this.resonatorEchoDao.add(this.resonatorEcho1);
        this.resonatorEchoDao.add(this.resonatorEcho2);
        this.resonatorEchoDao.add(this.resonatorEcho3);

        List<ResonatorEcho> resonatorEchos = this.resonatorEchoDao.getAllByPresetId(1);
        List<ResonatorEcho> expected = List.of(this.resonatorEcho1, this.resonatorEcho2, this.resonatorEcho3);

        assertThat(resonatorEchos).usingRecursiveComparison().isEqualTo(expected);
    }
}
