package com.sjb.wuwaechorank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.validstat.ValidStatDao;
import com.sjb.wuwaechorank.dao.entity.weapon.WeaponDao;
import com.sjb.wuwaechorank.dto.ResonatorDetailDto;
import com.sjb.wuwaechorank.dto.ResonatorsInfoDto;
import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Resonator;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.entity.Weapon;
import com.sjb.wuwaechorank.service.resonator.ResonatorService;
import com.sjb.wuwaechorank.service.resonator.ResonatorServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ResonatorServiceTest {
    @Mock
    ResonatorDao resonatorDao;

    @Mock
    AttributeDao attributeDao;

    @Mock
    WeaponDao weaponDao;

    @Mock
    ValidStatDao validStatDao;

    @Mock
    SubStatDao subStatDao;

    @InjectMocks
    ResonatorService resonatorService = new ResonatorServiceImpl(resonatorDao, attributeDao, weaponDao, validStatDao,
            subStatDao);

    Resonator resonator1;
    Resonator resonator2;
    Resonator resonator3;

    Attribute attribute1;
    Attribute attribute2;
    Attribute attribute3;

    Weapon weapon1;
    Weapon weapon2;

    ValidStat validStat1;

    SubStat subStat1;
    SubStat subStat2;
    SubStat subStat3;
    SubStat subStat4;
    SubStat subStat5;

    @BeforeEach
    void setUp() {
        this.resonator1 = new Resonator(1, "카르티시아", 1, 1, 5, 1, "asdf/qwer/a.jpg");
        this.resonator2 = new Resonator(2, "에이메스", 2, 1, 5, 3, "asdf/qwer/b.jpg");
        this.resonator3 = new Resonator(3, "유노", 3, 2, 5, 3, "asdf/qwer/c.jpg");

        this.attribute1 = new Attribute(1, "용융", "asdf/qwer/a.jpg");
        this.attribute2 = new Attribute(2, "회절", "asdf/qwer/b.jpg");
        this.attribute3 = new Attribute(3, "전도", "asdf/qwer/c.jpg");

        this.weapon1 = new Weapon(1, "직검", "asdf/qwer/a.jpg");
        this.weapon2 = new Weapon(2, "권갑", "asdf/qwer/b.jpg");

        this.validStat1 = new ValidStat(1, 1, 2, 3, 4, 5);

        this.subStat1 = new SubStat(1, "체력%");
        this.subStat2 = new SubStat(2, "크리티컬 확률");
        this.subStat3 = new SubStat(3, "크리티컬 피해");
        this.subStat4 = new SubStat(4, "공격력%");
        this.subStat5 = new SubStat(5, "일반공격피해");
    }

    // 전체 공명자 정보 반환 테스트
    @Test
    void getAllResonatorInfo() {
        List<Resonator> resonators = new ArrayList<>();
        resonators.add(resonator1);
        resonators.add(resonator2);
        resonators.add(resonator3);

        when(this.resonatorDao.getAll()).thenReturn(resonators);

        when(this.attributeDao.get(1)).thenReturn(attribute1);
        when(this.attributeDao.get(2)).thenReturn(attribute2);
        when(this.attributeDao.get(3)).thenReturn(attribute3);

        when(this.weaponDao.get(1)).thenReturn(weapon1);
        when(this.weaponDao.get(2)).thenReturn(weapon2);

        List<ResonatorsInfoDto> result = resonatorService.getAllResonatorInfo();

        int index = 0;
        for (ResonatorsInfoDto resonatorInfoDto : result) {
            assertEquals(resonators.get(index).getId(), resonatorInfoDto.getId());
            assertEquals(resonators.get(index).getName(), resonatorInfoDto.getName());
            assertEquals(resonators.get(index).getStar(), resonatorInfoDto.getStar());
            assertEquals(resonators.get(index).getValidStatId(), resonatorInfoDto.getValidateStatId());
            assertEquals(resonators.get(index).getImagePath(), resonatorInfoDto.getImagePath());
            index++;
        }
        assertEquals(attribute1, result.get(0).getAttribute());
        assertEquals(weapon1, result.get(0).getWeapon());

        assertEquals(attribute2, result.get(1).getAttribute());
        assertEquals(weapon1, result.get(1).getWeapon());

        assertEquals(attribute3, result.get(2).getAttribute());
        assertEquals(weapon2, result.get(2).getWeapon());

        verify(attributeDao).get(1);
        verify(attributeDao).get(2);
        verify(attributeDao).get(3);
        verify(weaponDao, atLeastOnce()).get(1);
        verify(weaponDao).get(2);
    }

    @Test
    void getResonatorDetail() {
        when(this.resonatorDao.get(1)).thenReturn(resonator1);
        when(this.attributeDao.get(1)).thenReturn(attribute1);
        when(this.weaponDao.get(1)).thenReturn(weapon1);
        when(this.validStatDao.get(1)).thenReturn(validStat1);
        when(this.subStatDao.get(1)).thenReturn(subStat1);
        when(this.subStatDao.get(2)).thenReturn(subStat2);
        when(this.subStatDao.get(3)).thenReturn(subStat3);
        when(this.subStatDao.get(4)).thenReturn(subStat4);
        when(this.subStatDao.get(5)).thenReturn(subStat5);

        String[] validStats = { subStat1.getName(), subStat2.getName(), subStat3.getName(), subStat4.getName(),
                subStat5.getName() };

        ResonatorDetailDto actualDto = this.resonatorService.getResonatorDetail(1);
        ResonatorDetailDto expectedDto = ResonatorDetailDto.builder()
                .id(resonator1.getId())
                .name(resonator1.getName())
                .attribute(attribute1)
                .weapon(weapon1)
                .star(resonator1.getStar())
                .validStats(validStats)
                .imagePath(resonator1.getImagePath())
                .build();

        assertThat(actualDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }
}
