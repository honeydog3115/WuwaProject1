package com.sjb.wuwaechorank.service.mainstat;

import java.util.List;

import com.sjb.wuwaechorank.entity.MainStat;

// 주음속성 서비스 인터페이스
public interface MainStatService {
    // 모든 주음속성 정보 반환
    List<MainStat> getAllMainStats();
}
