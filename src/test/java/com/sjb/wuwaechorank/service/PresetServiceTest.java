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
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.preset.PresetDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dto.PresetInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;
import com.sjb.wuwaechorank.dto.ResonatorEchoSubStatDto;
import com.sjb.wuwaechorank.dto.SimplePresetInfoDto;
import com.sjb.wuwaechorank.entity.Preset;
import com.sjb.wuwaechorank.entity.ResonatorEcho;
import com.sjb.wuwaechorank.service.preset.PresetService;
import com.sjb.wuwaechorank.service.preset.PresetServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PresetServiceTest {
    @Mock
    PresetDao presetDao;

    @Mock
    ResonatorEchoDao resonatorEchoDao;

    @InjectMocks
    PresetService presetService = new PresetServiceImpl(presetDao, resonatorEchoDao);

    @Captor
    ArgumentCaptor<Preset> presetCaptor;

    @Captor
    ArgumentCaptor<ResonatorEcho> resonatorEchoCaptor;

    PresetInfoDto presetInfoDto1;
    List<ResonatorEchoInfoDto> resonatorEchoInfoDto; 
    List<ResonatorEchoSubStatDto> resonatorEchoSubStatDtos;

    Preset preset1;
    Preset preset2;
    Preset preset3;

    ResonatorEcho resonatorEcho1;
    
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
        
        this.presetInfoDto1 = PresetInfoDto.builder()
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
                .SubStatId1(resonatorEchoSubStatDtos.get(0).subStatId())
                .SubStatId2(resonatorEchoSubStatDtos.get(1).subStatId())
                .SubStatId3(resonatorEchoSubStatDtos.get(2).subStatId())
                .SubStatId4(resonatorEchoSubStatDtos.get(3).subStatId())
                .SubStatId5(null)
                .build();
    }

    @Test
    void savePreset(){
        this.presetService.savePreset(presetInfoDto1);

        verify(this.presetDao).add(presetCaptor.capture());
        Preset preset = presetCaptor.getValue();

        verify(this.resonatorEchoDao).add(resonatorEchoCaptor.capture());
        ResonatorEcho resonatorEcho = resonatorEchoCaptor.getValue();

        assertThat(preset).usingRecursiveComparison().isEqualTo(preset1);
        assertThat(resonatorEcho).usingRecursiveComparison().isEqualTo(resonatorEcho1);
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
}
