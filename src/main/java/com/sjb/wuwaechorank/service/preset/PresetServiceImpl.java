package com.sjb.wuwaechorank.service.preset;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.mainstat.MainStatDao;
import com.sjb.wuwaechorank.dao.entity.preset.PresetDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.EchoDetailDto;
import com.sjb.wuwaechorank.dto.PresetInputInfoDto;
import com.sjb.wuwaechorank.dto.PresetOutputInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;
import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.MainStat;
import com.sjb.wuwaechorank.entity.Preset;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.service.resonator.ResonatorService;
import com.sjb.wuwaechorank.service.resonatorecho.ResonatorEchoService;

// PresetService 인터페이스 구현체
@Service
public class PresetServiceImpl implements PresetService {
    private PresetDao presetDao;
    private ResonatorEchoDao resonatorEchoDao;
    private ResonatorService resonatorService;
    private ResonatorEchoService resonatorEchoService;
    private EchoDao echoDao;
    private MainStatDao mainStatDao;
    private SubStatDao subStatDao;
    private SubStatInfoDao subStatInfoDao;
    private EchoSubStatInfoDao echoSubStatInfoDao;

    public PresetServiceImpl(PresetDao presetDao, ResonatorEchoDao resonatorEchoDao,
            ResonatorService resonatorService, ResonatorEchoService resonatorEchoService, EchoDao echoDao,
            MainStatDao mainStatDao, SubStatDao subStatDao, SubStatInfoDao subStatInfoDao,
            EchoSubStatInfoDao echoSubStatInfoDao) {
        this.presetDao = presetDao;
        this.resonatorEchoDao = resonatorEchoDao;
        this.resonatorService = resonatorService;
        this.resonatorEchoDao = resonatorEchoDao;
        this.echoDao = echoDao;
        this.mainStatDao = mainStatDao;
        this.subStatDao = subStatDao;
        this.subStatInfoDao = subStatInfoDao;
        this.echoSubStatInfoDao = echoSubStatInfoDao;
    }

    @Override
    public void savePreset(PresetInputInfoDto presetInfoDto) {
        List<ResonatorEchoInfoDto> resonatorEchoInfos = presetInfoDto.echosInfo();
        int presetId = this.presetDao.add(Preset.builder()
                .userId(presetInfoDto.userId())
                .name(presetInfoDto.name())
                .resonatorId(presetInfoDto.resonatorId())
                .echoTotalScore(presetInfoDto.score())
                .bookmark(false)
                .build());

        resonatorEchoService.getResonatorEchoScore(presetInfoDto.resonatorId(), resonatorEchoInfos, true,
                presetId);
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
        // TODO Auto-generated method stub
        Preset preset = this.presetDao.get(presestId);
        List<ResonatorEcho> resonatorEchos = resonatorEchoDao.getAllByPresetId(presestId);
        List<EchoDetailDto> echoDetailDtos = resonatorEchos.stream()
                .map(this::getEchoDetail)
                .toList();

        return PresetOutputInfoDto.builder()
                .presetId(presestId)
                .presetName(preset.getName())
                .resonatorDetailDto(resonatorService.getResonatorDetail(preset.getResonatorId()))
                .echoDetailDtos(echoDetailDtos)
                .totalScore(preset.getEchoTotalScore())
                .build();
    }

    // 에코들의 정보(에코 엔티티, 메인 속성, 부음 속성들)을 가져옴
    private EchoDetailDto getEchoDetail(ResonatorEcho resonatorEcho) {
        Echo echo = this.echoDao.get(resonatorEcho.getEchoId());
        MainStat mainstat = this.mainStatDao.get(resonatorEcho.getMainStatId());
        List<Integer> echoSubStatInfoIds = this.echoSubStatInfoDao.getIdsByResonatorEchoId(resonatorEcho.getId());
        List<SubStatDetailDto> subStatDetailDtos = this.subStatInfoDao.getAllByEchoSubStatInfos(echoSubStatInfoIds).stream()
                .map(subStatInfo -> SubStatDetailDto.builder()
                        .subStatValue(this.subStatDao.get(subStatInfo.getSubStatId()).getName())
                        .subStatValue(subStatInfo.getValue())
                        .subStatChance(subStatInfo.getChance())
                        .build())
                .toList();
        return EchoDetailDto.builder()
                .echo(echo)
                .mainstat(mainstat)
                .subStatDetailDtos(subStatDetailDtos)
                .build();
    }
}
