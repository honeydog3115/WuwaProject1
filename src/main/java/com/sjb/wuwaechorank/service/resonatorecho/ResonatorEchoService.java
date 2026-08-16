package com.sjb.wuwaechorank.service.resonatorecho;

import java.util.List;

import com.sjb.wuwaechorank.dto.EchoDetailDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.entity.ResonatorEcho;

// 공명자의 에코와 관련된 서비스 인터페이스
public interface ResonatorEchoService {
    // resonatorEcho로 에코, 주속성, 부음속성 값을 반환하는 함수
    EchoDetailDto getEchoDetail(ResonatorEcho resonatorEcho);

    // presetId에 해당하는 공명자 에코 전부 가져오는 함수
    List<ResonatorEcho> getResonatorEchosByPresetId(int presetId);

    // ResonatorEcho를 저장하는 함수
    void saveResonatorEchos(int presetId, List<ResonatorEchoInfoDto> resonatorEchoInfos);
}
