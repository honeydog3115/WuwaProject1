package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;

import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;

public interface EchoScoreService {
    // 공명자가 착용한 에코들의 점수를 구해서 총 점수를 반환하는 함수.
    List<Double> getResonatorEchoScore(int resonatorId, List<ResonatorEchoInfoDto> resonatorEchosInfo);
}
