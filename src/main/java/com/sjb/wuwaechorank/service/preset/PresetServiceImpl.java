package com.sjb.wuwaechorank.service.preset;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.preset.PresetDao;
import com.sjb.wuwaechorank.dto.EchoDetailDto;
import com.sjb.wuwaechorank.dto.PresetInputInfoDto;
import com.sjb.wuwaechorank.dto.PresetOutputInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;
import com.sjb.wuwaechorank.entity.Preset;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.service.resonator.ResonatorService;
import com.sjb.wuwaechorank.service.resonatorecho.EchoScoreService;
import com.sjb.wuwaechorank.service.resonatorecho.ResonatorEchoService;

// PresetService 인터페이스 구현체
@Service
public class PresetServiceImpl implements PresetService {
    private PresetDao presetDao;
    private ResonatorService resonatorService;
    private ResonatorEchoService resonatorEchoService;
    private EchoScoreService echoScoreService;

    public PresetServiceImpl(PresetDao presetDao, ResonatorService resonatorService, EchoScoreService echoScoreService, ResonatorEchoService resonatorEchoService) {
        this.presetDao = presetDao;
        this.resonatorService = resonatorService;
        this.resonatorEchoService = resonatorEchoService;
        this.echoScoreService = echoScoreService;
    }

    @Override
    public int savePreset(PresetInputInfoDto presetInfoDto) {
        List<ResonatorEchoInfoDto> resonatorEchoInfos = presetInfoDto.echosInfo();
        int presetId = this.presetDao.add(Preset.builder()
                .userId(presetInfoDto.userId())
                .name(presetInfoDto.name())
                .resonatorId(presetInfoDto.resonatorId())
                .echoTotalScore(presetInfoDto.score())
                .bookmark(presetInfoDto.bookmark())
                .build());

        this.echoScoreService.getResonatorEchoScore(presetInfoDto.resonatorId(), resonatorEchoInfos, true,
                presetId);

        return presetId;
    }

    @Override
    public void updatePreset(int presetId, PresetInputInfoDto presetInfoDto) {
        this.presetDao.update(presetId, Preset.builder()
                .userId(presetInfoDto.userId())
                .name(presetInfoDto.name())
                .resonatorId(presetInfoDto.resonatorId())
                .echoTotalScore(presetInfoDto.score())
                .bookmark(presetInfoDto.bookmark())
                .build());
    }

    @Override
    public List<SimplePresetInfoDto> getSimplePresetInfo(int userId) {
        List<Preset> presets = this.presetDao.getAllByUserId(userId);
        List<SimplePresetInfoDto> simplePresetInfos = presets.stream()
                .map(preset -> SimplePresetInfoDto.builder()
                        .id(preset.getId())
                        .name(preset.getName())
                        .bookmark(preset.getBookmark())
                        .build())
                .toList();
        return simplePresetInfos;
    }

    @Override
    public PresetOutputInfoDto getPresetInfo(int presestId) {
        Preset preset = this.presetDao.get(presestId);
        List<ResonatorEcho> resonatorEchos = this.resonatorEchoService.getResonatorEchosByPresetId(presestId);
        List<EchoDetailDto> echoDetailDtos = resonatorEchos.stream()
                .map(resonatorEcho->this.resonatorEchoService.getEchoDetail(resonatorEcho))
                .toList();

        return PresetOutputInfoDto.builder()
                .presetId(presestId)
                .presetName(preset.getName())
                .resonatorDetailDto(resonatorService.getResonatorDetail(preset.getResonatorId()))
                .echoDetailDtos(echoDetailDtos)
                .totalScore(preset.getEchoTotalScore())
                .build();
    }
}
