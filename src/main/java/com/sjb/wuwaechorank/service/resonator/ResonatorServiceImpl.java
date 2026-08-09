package com.sjb.wuwaechorank.service.resonator;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.dao.entity.validstat.ValidStatDao;
import com.sjb.wuwaechorank.dao.entity.weapon.WeaponDao;
import com.sjb.wuwaechorank.dto.ResonatorInfoDto;
import com.sjb.wuwaechorank.entity.Resonator;


// 공명자 서비스
@Service
public class ResonatorServiceImpl implements ResonatorService {
    private ResonatorDao resonatorDao;
    private AttributeDao attributeDao;
    private WeaponDao weaponDao;

    public ResonatorServiceImpl(ResonatorDao resonatorDao, AttributeDao attributeDao, WeaponDao weaponDao){
        this.resonatorDao = resonatorDao;
    }

    // 전체 공명자 정보 반환
    // 현재 코드로는 공명자가 100명이 되면 SQL을 300개는 던지게됨.
    // 따라서 나중에 Map으로 Attribute나 Weapon을 캐싱하면 좋을 것 같음.
    // 혹은 다른 방법도 생각해 보자.
    public List<ResonatorInfoDto> getAllResonatorInfo(){
        List<Resonator> resonators = resonatorDao.getAll();
        List<ResonatorInfoDto> resonatorInfoDtos = new ArrayList<>();
        for (Resonator resonator : resonators) {
            ResonatorInfoDto resonatorInfoDto = new ResonatorInfoDto();
            resonatorInfoDto.setId(resonator.getId());
            resonatorInfoDto.setName(resonator.getName());
            resonatorInfoDto.setAttribute(this.attributeDao.get(resonator.getAttributeId()));
            resonatorInfoDto.setWeapon(this.weaponDao.get(resonator.getWeaponId()));
            resonatorInfoDto.setStar(resonator.getStar());
            resonatorInfoDto.setValidateStatId(resonator.getValidStatId());
            resonatorInfoDto.setImagePath(resonator.getImagePath());
            resonatorInfoDtos.add(resonatorInfoDto);
        }
        return resonatorInfoDtos;
    }


}
