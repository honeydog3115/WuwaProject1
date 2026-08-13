package com.sjb.wuwaechorank.service.preset;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.preset.PresetDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dto.PresetInputInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoSubStatDto;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;
import com.sjb.wuwaechorank.entity.Preset;
import com.sjb.wuwaechorank.entity.ResonatorEcho;

// PresetService 인터페이스 구현체
@Service
public class PresetServiceImpl implements PresetService {
    private PresetDao presetDao;
    private ResonatorEchoDao resonatorEchoDao;

    public PresetServiceImpl(PresetDao presetDao, ResonatorEchoDao resonatorEchoDao) {
        this.presetDao = presetDao;
        this.resonatorEchoDao = resonatorEchoDao;
    }

    @Override
    public void savePreset(PresetInputInfoDto presetInfoDto) {
        this.presetDao.add(
                Preset.builder()
                        .userId(presetInfoDto.userId())
                        .name(presetInfoDto.name())
                        .resonatorId(presetInfoDto.resonatorId())
                        .echoTotalScore(presetInfoDto.score())
                        .bookmark(false)
                        .build());

        List<ResonatorEchoInfoDto> resonatorEchoInfos = presetInfoDto.echosInfo();
        resonatorEchoInfos.stream()
                .forEach(resonatorEchoInfo -> this.resonatorEchoDao.add(ResonatorEcho.builder()
                        .echoId(resonatorEchoInfo.echoId())
                        .SubStatId1(this.getOrNull(resonatorEchoInfo.echoSubStats(), 0))
                        .SubStatId2(this.getOrNull(resonatorEchoInfo.echoSubStats(), 1))
                        .SubStatId3(this.getOrNull(resonatorEchoInfo.echoSubStats(), 2))
                        .SubStatId4(this.getOrNull(resonatorEchoInfo.echoSubStats(), 3))
                        .SubStatId5(this.getOrNull(resonatorEchoInfo.echoSubStats(), 4))
                        .build()));
    }

    @Override
    public List<SimplePresetInfoDto> getSimplePresetInfo(int userId) {
        List<Preset> presets = this.presetDao.getAllByUserId(userId);
        List<SimplePresetInfoDto> simplePresetInfos = presets.stream()
                .map(preset->SimplePresetInfoDto.builder()
                        .id(preset.getId())
                        .name(preset.getName())
                        .bookmark(preset.getBookmark())
                        .build())
                .toList();
        return simplePresetInfos;
    }



    // 리스트의 범위를 벗어나면 null을 주는 함수
    private Integer getOrNull(List<ResonatorEchoSubStatDto> echoSubStats, int index) {
        return echoSubStats.size() <= index ? null : echoSubStats.get(index).subStatId();
    }
}
