package com.sjb.wuwaechorank.service.preset;

import com.sjb.wuwaechorank.dto.PresetInfoDto;

// 프리셋 관련 서비스 인터페이스
public interface PresetService {
    // 프리셋 저장 함수
    void savePreset(PresetInfoDto presetInfoDto);
}