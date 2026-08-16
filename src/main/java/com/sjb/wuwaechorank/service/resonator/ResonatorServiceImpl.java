package com.sjb.wuwaechorank.service.resonator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.attribute.AttributeDao;
import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.validstat.ValidStatDao;
import com.sjb.wuwaechorank.dao.entity.weapon.WeaponDao;
import com.sjb.wuwaechorank.dto.ResonatorDetailDto;
import com.sjb.wuwaechorank.dto.ResonatorFilterDto;
import com.sjb.wuwaechorank.dto.ResonatorsInfoDto;
import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Resonator;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.entity.Weapon;


// 공명자 서비스
@Service
public class ResonatorServiceImpl implements ResonatorService {
    private ResonatorDao resonatorDao;
    private AttributeDao attributeDao;
    private WeaponDao weaponDao;
    private ValidStatDao validStatDao;
    private SubStatDao subStatDao;

    public ResonatorServiceImpl(ResonatorDao resonatorDao, AttributeDao attributeDao, WeaponDao weaponDao, ValidStatDao validStatDao, SubStatDao subStatDao){
        this.resonatorDao = resonatorDao;
    }

    // 전체 공명자 정보 반환
    // 현재 코드로는 공명자가 100명이 되면 SQL을 300개는 던지게됨.
    // 따라서 나중에 Map으로 Attribute나 Weapon을 캐싱하면 좋을 것 같음.
    // 혹은 다른 방법도 생각해 보자.
    @Override
    public List<ResonatorsInfoDto> getAllResonatorInfo(){
        List<Resonator> resonators = resonatorDao.getAll();
        List<ResonatorsInfoDto> resonatorInfoDtos = new ArrayList<>();
        for (Resonator resonator : resonators) {
            ResonatorsInfoDto resonatorInfoDto = new ResonatorsInfoDto();
            resonatorInfoDto.setId(resonator.getId());
            resonatorInfoDto.setName(resonator.getName());
            resonatorInfoDto.setAttribute(this.attributeDao.get(resonator.getAttributeId()));
            resonatorInfoDto.setWeapon(this.weaponDao.get(resonator.getWeaponId()));
            resonatorInfoDto.setStar(resonator.getStar());
            resonatorInfoDto.setImagePath(resonator.getImagePath());
            resonatorInfoDtos.add(resonatorInfoDto);
        }
        return resonatorInfoDtos;
    }

    // 공명자 선택시 공명자 세부 정보를 반환하는 함수
    @Override
    public ResonatorDetailDto getResonatorDetail(int id){
        Resonator resonator = this.resonatorDao.get(id);
        Attribute attribute = this.attributeDao.get(resonator.getAttributeId());
        Weapon weapon = this.weaponDao.get(resonator.getWeaponId());
        ValidStat validStat = this.validStatDao.get(resonator.getValidStatId());
        List<SubStat> subStats = Arrays.stream(validStat.getClass().getDeclaredFields())
                                    .filter(field->field.getName().contains("subStatId"))
                                    .map(field -> getSubStat(field, validStat))
                                    .toList();
        List<String> validStats = subStats.stream().map(subStat->subStat.getName()).toList();

        return ResonatorDetailDto.builder()
                .id(id)
                .name(resonator.getName())
                .attribute(attribute)
                .weapon(weapon)
                .star(resonator.getStar())
                .validStats(validStats)
                .energyRegenRequirements(resonator.getEnergyRegenRequirements())
                .imagePath(resonator.getImagePath())
                .build();
    }

    @Override
    public ResonatorFilterDto getResonatorFilter() {
        return ResonatorFilterDto.builder()
                .attributes(this.attributeDao.getAll())
                .weapons(this.weaponDao.getAll())
                .build();
    }

    private SubStat getSubStat(Field field, ValidStat validStat){
        try {
            field.setAccessible(true);
            return this.subStatDao.get(field.get(validStat));
        } catch (Exception e) {
            throw new IllegalArgumentException("getResonatorDetail() 예외 발생", e);
        }
    }


    public ValidStat getResonatorValidStat(int id){
        return this.validStatDao.get(this.resonatorDao.get(id).getValidStatId());
    }
}
