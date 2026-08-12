package com.sjb.wuwaechorank.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.presetecho.PresetEchoDao;
import com.sjb.wuwaechorank.entity.Preset;
import com.sjb.wuwaechorank.entity.PresetEcho;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.DaoTestUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class PresetEchoDaoTest {
    private static final String TABLE_NAME = "presetecho";

    @Autowired
    PresetEchoDao presetEchoDao;

    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    TestFixture testFixture;

    PresetEcho presetEcho1;
    PresetEcho presetEcho2;
    PresetEcho presetEcho3;

    ResonatorEcho resonatorEcho2;
    ResonatorEcho resonatorEcho3;

    @BeforeEach
    void setUp(){
        this.testFixture.createReferenceEntity(PresetEcho.class);
        this.daoJDBCUtil.setTestFixture(testFixture);
        this.daoJDBCUtil.initTables(TABLE_NAME);
        this.daoJDBCUtil.initReferenceTables();

        this.presetEcho1 = new PresetEcho(1, 1, 1);
        this.presetEcho2 = new PresetEcho(1, 1, 2);
        this.presetEcho3 = new PresetEcho(1, 1, 3);

        this.resonatorEcho2 = new ResonatorEcho(2, 1, 1, 1, 1, 1, 1, 1, 0);
        this.resonatorEcho3 = new ResonatorEcho(3, 1, 1, 1, 1, 1, 1, 1, 0);
        this.daoJDBCUtil.addRefEntity(resonatorEcho2);
        this.daoJDBCUtil.addRefEntity(resonatorEcho3);
    }

    @Test
    void addAndGet(){
        this.presetEchoDao.add(presetEcho1);
        PresetEcho presetEcho = this.presetEchoDao.get(1);

        assertThat(presetEcho).usingRecursiveComparison().isEqualTo(presetEcho1);
    }

    @Test
    void getAll(){
        this.presetEchoDao.add(presetEcho1);
        this.presetEchoDao.add(presetEcho2);
        this.presetEchoDao.add(presetEcho3);
        
        List<PresetEcho> presetEchos = this.presetEchoDao.getAll();
        assertThat(presetEchos.size()).isEqualTo(3);
    }
    
    @Test
    void deleteAndgetCount(){
        this.presetEchoDao.add(presetEcho1);
        this.presetEchoDao.delete(1);

        assertThat(this.presetEchoDao.getCount()).isEqualTo(0);
    }

    @Test
    void update(){
        this.presetEchoDao.add(presetEcho1);

        PresetEcho presetEcho = new PresetEcho(1, 1, 2);
        this.presetEchoDao.update(1, presetEcho);
        
        assertThat(this.presetEchoDao.get(1)).usingRecursiveComparison().isEqualTo(presetEcho);
    }
    
    @Test
    void foreignKeyConstraintViolation(){
        PresetEcho invalidPresetEcho1 = new PresetEcho(1, 1, 4);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.presetEchoDao.add(invalidPresetEcho1));
        PresetEcho invalidPresetEcho2 = new PresetEcho(1, 2, 3);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.presetEchoDao.add(invalidPresetEcho2));
    }

    @ParameterizedTest(name="{0} 삭제시 연쇄 삭제 테스트")
    @ValueSource(classes = {
        ResonatorEcho.class,
        Preset.class
    })
    void deleteCasCade(Class<?> refEntiyClass){
        this.presetEchoDao.add(presetEcho1);
        daoJDBCUtil.deleteRefEntity(refEntiyClass);
        assertThat(this.presetEchoDao.getCount()).isEqualTo(0);
    }
}
