package com.sjb.wuwaechorank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.dao.entity.weapon.WeaponDao;
import com.sjb.wuwaechorank.dto.ResonatorInfoDto;
import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Resonator;
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

    @InjectMocks
    ResonatorService resonatorService = new ResonatorServiceImpl(resonatorDao, attributeDao, weaponDao);

    // 전체 공명자 정보 반환 테스트
    @Test
    void getAllResonatorInfo(){
        Resonator resonator1 = new Resonator(1, "카르티시아", 1, 1, 5, 1, "asdf/qwer/a.jpg");
        Resonator resonator2 = new Resonator(2, "에이메스", 2, 1, 5,3,  "asdf/qwer/b.jpg");
        Resonator resonator3 = new Resonator(3, "유노", 3, 2, 5, 3, "asdf/qwer/c.jpg");
        
        Attribute attribute1 = new Attribute(1, "용융", "asdf/qwer/a.jpg");
        Attribute attribute2 = new Attribute(2, "회절", "asdf/qwer/b.jpg");
        Attribute attribute3 = new Attribute(3, "전도", "asdf/qwer/c.jpg");

        Weapon weapon1 = new Weapon(1, "직검", "asdf/qwer/a.jpg");
        Weapon weapon2 = new Weapon(2, "권갑", "asdf/qwer/b.jpg");
        Weapon weapon3 = new Weapon(3, "권총", "asdf/qwer/c.jpg");

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

        List<ResonatorInfoDto> result = resonatorService.getAllResonatorInfo();

        int index = 0;
        for (ResonatorInfoDto resonatorInfoDto : result) {
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

}
