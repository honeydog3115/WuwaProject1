package com.sjb.wuwaechorank.service.preset;

import java.util.List;

import com.sjb.wuwaechorank.dto.PresetInputInfoDto;
import com.sjb.wuwaechorank.dto.PresetOutputInfoDto;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;

// 프리셋 관련 서비스 인터페이스
public interface PresetService {
    // 프리셋 저장 함수
    void savePreset(PresetInputInfoDto presetInfoDto);
    // 사용자의 프리셋들 기본 정보를 반환하는 함수
    List<SimplePresetInfoDto> getSimplePresetInfo(int userId);
    // 사용자가 프리셋을 눌렀을 때 해당 프리셋의 정보를 반환하는 함수
    PresetOutputInfoDto getPresetInfo(int presestId);
}