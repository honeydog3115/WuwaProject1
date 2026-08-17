package com.sjb.wuwaechorank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.mainstat.MainStatDao;
import com.sjb.wuwaechorank.dao.entity.presetecho.PresetEchoDao;
import com.sjb.wuwaechorank.dao.entity.resonatorecho.ResonatorEchoDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.entity.Resonator;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.service.resonatorecho.ResonatorEchoService;
import com.sjb.wuwaechorank.service.resonatorecho.ResonatorEchoServiceImpl;
import com.sjb.wuwaechorank.service.substat.SubStatService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ResonatorEchoServiceTest {
    @Mock
    SubStatInfoDao subStatInfoDao;

    @Mock
    ResonatorEchoDao resonatorEchoDao;

    @Mock
    EchoDao echoDao;
    
    @Mock
    EchoSubStatInfoDao echoSubStatInfoDao;
    
    @Mock
    PresetEchoDao presetEchoDao;

    @Mock
    MainStatDao mainStatDao;
    
    @Mock
    SubStatService subStatService;

    @InjectMocks
    ResonatorEchoService resonatorEchoService = new ResonatorEchoServiceImpl(resonatorEchoDao, subStatService, echoDao, mainStatDao, presetEchoDao, echoSubStatInfoDao);

    Resonator resonator1;

    SubStat subStat1;
    SubStat subStat2;
    SubStat subStat3;
    SubStat subStat4;
    SubStat subStat5;

    SubStatInfo subStatInfo1;
    SubStatInfo subStatInfo2;
    SubStatInfo subStatInfo3;
    SubStatInfo subStatInfo4;
    SubStatInfo subStatInfo5;
    SubStatInfo subStatInfo6;
    SubStatInfo subStatInfo7;
    SubStatInfo subStatInfo8;

    ValidStat validStat1;

    @BeforeEach
    void setUp() {
        this.resonator1 = new Resonator(1, "카르티시아", 1, 1, 5, 1, "120%", "asdf/qwer/a.jpg");

        this.subStat1 = new SubStat(1, "체력%");
        this.subStat2 = new SubStat(2, "크리티컬확률");
        this.subStat3 = new SubStat(3, "크리티컬피해");
        this.subStat4 = new SubStat(4, "일반공격피해");
        this.subStat5 = new SubStat(5, "공명효율");

        this.subStatInfo1 = SubStatInfo.builder().SubStatId(1).value("10%").build();
        this.subStatInfo2 = SubStatInfo.builder().SubStatId(1).value("20%").build();
        this.subStatInfo3 = SubStatInfo.builder().SubStatId(1).value("30%").build();
        this.subStatInfo4 = SubStatInfo.builder().SubStatId(1).value("40%").build();
        this.subStatInfo5 = SubStatInfo.builder().SubStatId(2).value("10%").build();
        this.subStatInfo6 = SubStatInfo.builder().SubStatId(3).value("10%").build();
        this.subStatInfo7 = SubStatInfo.builder().SubStatId(4).value("10%").build();
        this.subStatInfo8 = SubStatInfo.builder().SubStatId(5).value("10%").build();

        this.validStat1 = new ValidStat(1, 1, 2, 3, 4, 5, null);
    }

    // 공명자 에코 총점수 계산 서비스 테스트
    @Test
    void getResonatorEchoScore(){
        // when(this.subStatInfoDao.getAllBySubStatIdIn(List.of(1,2,3,4,5))).thenReturn(List.of(this.subStatInfo1, this.subStatInfo2, this.subStatInfo3, this.subStatInfo4, this.subStatInfo5, this.subStatInfo6, this.subStatInfo7, this.subStatInfo8));

        // List<ResonatorEchoInfoDto> resonatorEchoInfoDtos = List.of(
        //     ResonatorEchoInfoDto.builder()
        //         .echoId(1)
        //         .echoSubStats(List.of(
        //             ResonatorEchoSubStatDto.builder().subStatId(subStat1.getId()).value(subStatInfo2.getValue()).build(),
        //             ResonatorEchoSubStatDto.builder().subStatId(subStat2.getId()).value(subStatInfo5.getValue()).build(),
        //             ResonatorEchoSubStatDto.builder().subStatId(subStat3.getId()).value(subStatInfo6.getValue()).build(),
        //             ResonatorEchoSubStatDto.builder().subStatId(subStat4.getId()).value(subStatInfo7.getValue()).build(),
        //             ResonatorEchoSubStatDto.builder().subStatId(subStat5.getId()).value(subStatInfo8.getValue()).build()
        //         ))
        //         .build()
        // );
        
        // List<Double> scores = this.echoScore.getResonatorEchoScore(resonator1.getId(), resonatorEchoInfoDtos, );
        // Double score = scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        // assertThat(score).isEqualTo((double)90);
    }
}
