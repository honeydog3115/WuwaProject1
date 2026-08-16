package com.sjb.wuwaechorank.service.resonator;

import java.util.List;

import com.sjb.wuwaechorank.dto.ResonatorDetailDto;
import com.sjb.wuwaechorank.dto.ResonatorFilterDto;
import com.sjb.wuwaechorank.dto.ResonatorsInfoDto;
import com.sjb.wuwaechorank.entity.ValidStat;

public interface ResonatorService {
    List<ResonatorsInfoDto> getAllResonatorInfo();
    ResonatorDetailDto getResonatorDetail(int id);
    ResonatorFilterDto getResonatorFilter();
    // 공명자 id로 공명자 유효속성 반환하는 함수 추가
    ValidStat getResonatorValidStat(int id);
}