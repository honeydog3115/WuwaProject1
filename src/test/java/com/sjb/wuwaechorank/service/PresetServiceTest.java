package com.sjb.wuwaechorank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.mainstat.MainStatDao;
import com.sjb.wuwaechorank.dao.entity.preset.PresetDao;
import com.sjb.wuwaechorank.dao.entity.resonator.ResonatorDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.EchoDetailDto;
import com.sjb.wuwaechorank.dto.PresetInputInfoDto;
import com.sjb.wuwaechorank.dto.PresetOutputInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoSubStatDto;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;
import com.sjb.wuwaechorank.dto.SubStatDetailDto;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.EchoSubStatInfo;
import com.sjb.wuwaechorank.entity.MainStat;
import com.sjb.wuwaechorank.entity.Preset;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.service.preset.PresetService;
import com.sjb.wuwaechorank.service.preset.PresetServiceImpl;
import com.sjb.wuwaechorank.service.resonator.ResonatorService;
import com.sjb.wuwaechorank.service.resonatorecho.ResonatorEchoService;

@ExtendWith(MockitoExtension.class)
public class PresetServiceTest {
    @Mock
    PresetDao presetDao;
    @Mock
    ResonatorEchoDao resonatorEchoDao;
    @Mock
    ResonatorDao resonatorDao;
    @Mock
    ResonatorService resonatorService;
    @Mock EchoDao echoDao;
    @Mock MainStatDao mainStatDao;
    @Mock SubStatDao subStatDao;
    @Mock ResonatorEchoService resonatorEchoService;
    @Mock SubStatInfoDao subStatInfoDao;
    @Mock EchoSubStatInfoDao echoSubStatInfoDao;

    @InjectMocks
    PresetService presetService = new PresetServiceImpl(presetDao, resonatorEchoDao, resonatorService, resonatorEchoService, echoDao, mainStatDao, subStatDao, subStatInfoDao, echoSubStatInfoDao);

    @Captor
    ArgumentCaptor<Preset> presetCaptor;

    @Captor
    ArgumentCaptor<ResonatorEcho> resonatorEchoCaptor;

    PresetInputInfoDto presetInfoDto1;
    List<ResonatorEchoInfoDto> resonatorEchoInfoDto; 
    List<ResonatorEchoSubStatDto> resonatorEchoSubStatDtos;

    Preset preset1;
    Preset preset2;
    Preset preset3;

    ResonatorEcho resonatorEcho1;
    Echo echo1;
    MainStat mainStat1;
    SubStat subStat1;
    SubStatInfo subStatInfo1;
    EchoSubStatInfo echoSubStatInfo1;

    @BeforeEach
    void setUp(){
        this.resonatorEchoSubStatDtos = List.of(
            ResonatorEchoSubStatDto.builder().subStatId(1).value("10%").build(),
            ResonatorEchoSubStatDto.builder().subStatId(2).value("20%").build(),
            ResonatorEchoSubStatDto.builder().subStatId(3).value("30%").build(),
            ResonatorEchoSubStatDto.builder().subStatId(4).value("40%").build()
        );

        this.resonatorEchoInfoDto = List.of(ResonatorEchoInfoDto.builder()
                .echoId(1)
                .echoSubStats(this.resonatorEchoSubStatDtos)
                .build());
        
        this.presetInfoDto1 = PresetInputInfoDto.builder()
                .userId(1)
                .name("방랑자 프리셋")
                .resonatorId(1)
                .echosInfo(this.resonatorEchoInfoDto)
                .score(50)
                .build();

        this.preset1 = new Preset(1, "방랑자 프리셋", 1, false, 1, 50.0);
        this.preset2 = new Preset(1, "에이메스 프리셋", 1, false, 2, 52.0);
        this.preset3 = new Preset(1, "카르티시아 프리셋", 1, false, 3, 53.0);

        this.resonatorEcho1 = ResonatorEcho.builder()
                .echoId(this.resonatorEchoInfoDto.get(0).echoId())
                .build();
        this.echo1 = new Echo(1, "꾹꾹복어", 1, "1COST", "asdf/qwer/a.jpg");
        this.mainStat1 = new MainStat(1, "공격력", "30.5", "asdf/qwer/a.jpg");
        this.echoSubStatInfo1 = new EchoSubStatInfo(1, 1, 1);
        this.subStatInfo1 = SubStatInfo.builder().id(1).SubStatId(1).value("10%").build();
        this.subStat1 = new SubStat(1, "체력%");
    }

    @Test
    void savePreset(){
        this.presetService.savePreset(presetInfoDto1);

        verify(this.presetDao).add(presetCaptor.capture());
        Preset preset = presetCaptor.getValue();

        assertThat(preset).usingRecursiveComparison().isEqualTo(preset1);
    }

    @Test
    void getSimplePresetInfo(){
        List<Preset> presets = List.of(this.preset1, this.preset2, this.preset3);
        when(this.presetDao.getAllByUserId(1)).thenReturn(presets);

        List<SimplePresetInfoDto> simplePresetInfos = presets.stream()
                .map(preset->SimplePresetInfoDto.builder()
                        .id(preset.getId())
                        .name(preset.getName())
                        .bookmark(preset.getBookmark())
                        .build())
                .toList();

        assertThat(this.presetService.getSimplePresetInfo(1)).usingRecursiveComparison().isEqualTo(simplePresetInfos);
    }

    @Test
    void getPresetInfo(){
        when(this.presetDao.get(preset1.getId())).thenReturn(preset1);
        when(this.resonatorEchoDao.getAllByPresetId(preset1.getId())).thenReturn(List.of(this.resonatorEcho1));
        when(this.echoDao.get(echo1.getId())).thenReturn(echo1);
        when(this.mainStatDao.get(mainStat1.getId())).thenReturn(mainStat1);
        when(this.echoSubStatInfoDao.getIdsByResonatorEchoId(resonatorEcho1.getId())).thenReturn(List.of(echoSubStatInfo1.getId()));
        when(this.subStatInfoDao.getAllByEchoSubStatInfos(List.of(echoSubStatInfo1.getId()))).thenReturn(List.of(subStatInfo1));
        when(this.subStatDao.get(this.subStat1.getId())).thenReturn(this.subStat1);

        assertThat(this.presetService.getPresetInfo(preset1.getId())).usingRecursiveComparison().isEqualTo(
            PresetOutputInfoDto.builder()
                    .presetId(preset1.getId())
                    .presetName(preset1.getName())
                    .resonatorDetailDto(resonatorService.getResonatorDetail(preset1.getResonatorId()))
                    .echoDetailDtos(List.of(
                        EchoDetailDto.builder()
                                .echo(echo1)
                                .mainstat(mainStat1)
                                .subStatDetailDtos(List.of(
                                    SubStatDetailDto.builder()
                                            .subStatName(subStat1.getName())
                                            .subStatChance(subStatInfo1.getChance())
                                            .subStatValue(subStatInfo1.getValue())
                                            .build()
                                    )
                                )
                                .build()
                    ))
                    .totalScore(preset1.getEchoTotalScore())
                    .build()
        );

    }
}
