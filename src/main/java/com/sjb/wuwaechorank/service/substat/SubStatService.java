package com.sjb.wuwaechorank.service.substat;

import java.util.List;

import com.sjb.wuwaechorank.dto.SubStatWithSubStatInfoDto;

// 부음속성 관련    서비스 인터페이스
public interface SubStatService {
    List<SubStatWithSubStatInfoDto> getAllSubStatsWithSubStatInfo();
}
