package com.sjb.wuwaechorank.service.resonator;

import java.util.List;

import com.sjb.wuwaechorank.dto.ResonatorDetailDto;
import com.sjb.wuwaechorank.dto.ResonatorFilterDto;
import com.sjb.wuwaechorank.dto.ResonatorsInfoDto;

public interface ResonatorService {
    List<ResonatorsInfoDto> getAllResonatorInfo();
    ResonatorDetailDto getResonatorDetail(int id);
    ResonatorFilterDto getResonatorFilter();
}