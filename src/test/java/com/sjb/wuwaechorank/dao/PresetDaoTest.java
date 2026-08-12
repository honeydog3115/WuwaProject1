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

import com.sjb.wuwaechorank.dao.entity.preset.PresetDao;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;
import com.sjb.wuwaechorank.entity.Preset;
import com.sjb.wuwaechorank.entity.Resonator;
import com.sjb.wuwaechorank.entity.User;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;
import com.sjb.wuwaechorank.util.DaoTestUtil;
import com.sjb.wuwaechorank.util.TestFixture;

@SpringBootTest
public class PresetDaoTest {
    private static final String TABLE_NAME = "preset";
    @Autowired
    DaoJDBCUtil daoJDBCUtil;
    @Autowired
    TestFixture testFixture;

    @Autowired
    PresetDao presetDao;

    Preset preset1;
    Preset preset2;
    Preset preset3;
    Preset preset4;

    User user2;
    @BeforeEach
    void setUp(){
        daoJDBCUtil.initTables(TABLE_NAME);
        testFixture.createReferenceEntity(Preset.class);
        daoJDBCUtil.setTestFixture(testFixture);
        daoJDBCUtil.initReferenceTables();

        preset1 = new Preset(1, "카르티시아 프리셋", 1, false, 1, 60.15);
        preset2 = new Preset(2, "에이메스 프리셋", 1, false, 1, 30.15);
        preset3 = new Preset(3, "유노 프리셋", 1, false, 1, 0.15);
        preset4 = new Preset(4, "여별 프리셋", 2, false, 1, 20.0);

        user2 = new User(2);
        daoJDBCUtil.addRefEntity(user2);
    }

    @Test
    void addAndGet(){
        this.presetDao.add(preset1);
        Preset preset = this.presetDao.get(1);
        assertThat(preset).usingRecursiveComparison().isEqualTo(preset1);
    }

    @Test
    void getAll(){
        this.presetDao.add(preset1);
        this.presetDao.add(preset2);
        this.presetDao.add(preset3);
        List<Preset> presets = this.presetDao.getAll();

        assertEquals(3, presets.size());
    }

    @Test
    void deleteAndGetCount(){
        this.presetDao.add(preset1);
        this.presetDao.delete(1);
        assertEquals(0, this.presetDao.getCount());
    }

    @Test
    void update(){
        this.presetDao.add(preset1);
        Preset preset = new Preset(1, "양양 프리셋", 1, true, 1, 77.7);
        this.presetDao.update(1, preset);
        Preset updatedPreset = this.presetDao.get(1);

        assertThat(updatedPreset).usingRecursiveComparison().isEqualTo(preset);
    }

    @Test
    void foreignKeyConstraintViolation(){
        this.preset1.setUserId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.presetDao.add(preset1));
        this.preset3.setResonatorId(2);
        DaoTestUtil.foreignKeyConstraintViolationTest(()->this.presetDao.add(preset3));
    }

    @ParameterizedTest(name="{0} 삭제시 null 할당 테스트")
    @ValueSource(classes = {
        User.class,
        Resonator.class,
    })
    void onDeleteSetNull(Class<?> refEntityClass){
        this.presetDao.add(preset1);
        this.daoJDBCUtil.deleteRefEntity(refEntityClass);
        Preset preset = this.presetDao.get(1);
        if (refEntityClass == User.class)
            assertThat(preset.getUserId()).isEqualTo(null);
        if (refEntityClass == Resonator.class)
            assertThat(preset.getResonatorId()).isEqualTo(null);
    }

    @Test
    void getAllByUserId(){
        this.presetDao.add(preset1);
        this.presetDao.add(preset2);
        this.presetDao.add(preset3);
        this.presetDao.add(preset4);
        Preset presetInfo1 = Preset.builder()
                .id(preset1.getId())
                .name(preset1.getName())
                .bookmark(preset1.getBookmark())
                .build();
        Preset presetInfo2 = Preset.builder()
                .id(preset2.getId())
                .name(preset2.getName())
                .bookmark(preset2.getBookmark())
                .build();
        Preset presetInfo3 = Preset.builder()
                .id(preset3.getId())
                .name(preset3.getName())
                .bookmark(preset3.getBookmark())
                .build();
        assertThat(this.presetDao.getAllByUserId(1)).usingRecursiveComparison().isEqualTo(List.of(presetInfo1, presetInfo2, presetInfo3));
    }    
}
