package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;

import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;

// 공명자의 에코와 관련된 서비스 인터페이스
public interface ResonatorEchoService {
    // 공명자가 착용한 에코들의 점수를 구해서 총 점수를 반환하는 함수.
    double getResonatorEchoScore(int resonatorId, List<ResonatorEchoInfoDto> resonatorEchosInfo, boolean insertDB, int presetId);
}
