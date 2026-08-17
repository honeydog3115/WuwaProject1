package com.sjb.wuwaechorank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.echosubstatinfo.EchoSubStatInfoDao;
import com.sjb.wuwaechorank.dao.entity.substat.SubStatDao;
import com.sjb.wuwaechorank.dao.entity.substatinfo.SubStatInfoDao;
import com.sjb.wuwaechorank.dto.SubStatWithSubStatInfoDto;
import com.sjb.wuwaechorank.entity.SubStat;
import com.sjb.wuwaechorank.entity.SubStatInfo;
import com.sjb.wuwaechorank.service.substat.SubStatService;
import com.sjb.wuwaechorank.service.substat.SubStatServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SubStatServiceTest {
    @Mock
    SubStatDao subStatDao;

    @Mock
    SubStatInfoDao subStatInfoDao;

    @Mock
    EchoSubStatInfoDao echoSubStatInfoDao;

    @InjectMocks
    SubStatService subStatService = new SubStatServiceImpl(subStatDao, subStatInfoDao, echoSubStatInfoDao);

    SubStat subStat1;
    SubStat subStat2;
    SubStat subStat3;

    SubStatInfo subStatInfo1;
    SubStatInfo subStatInfo2;
    SubStatInfo subStatInfo3;
    SubStatInfo subStatInfo4;

    @BeforeEach
    void setUp(){
        this.subStat1 = new SubStat(1, "체력%");
        this.subStat2 = new SubStat(2, "크리티컬 확률");
        this.subStat3 = new SubStat(3, "크리티컬 피해");

        this.subStatInfo1 = new SubStatInfo(1, 1, "15.5%", "12.1%");
        this.subStatInfo2 = new SubStatInfo(2, 1, "12.0%", "23.7%");
        this.subStatInfo3 = new SubStatInfo(3, 2, "18.5%", "5.2%");
        this.subStatInfo4 = new SubStatInfo(4, 3, "20.5%", "1.2%");
    }

    // 에코의 부음속성을 선택할 때 필요한 정보들을 반환하는 함수 테스트
    @Test
    void getAllSubStatsWithSubStatInfo(){
        List<SubStat> subStats = List.of(subStat1, subStat2, subStat3);
        List<SubStatInfo> subStatInfos = List.of(subStatInfo1, subStatInfo2, subStatInfo3, subStatInfo4);

        when(this.subStatDao.getAll()).thenReturn(subStats);
        when(this.subStatInfoDao.getAll()).thenReturn(subStatInfos);

        Map<Integer, List<SubStatInfo>> subStatInfoGroupBySubStat = subStatInfos.stream()
                .collect(Collectors.groupingBy(SubStatInfo::getSubStatId));
        
        List<SubStatWithSubStatInfoDto> expected =  subStats.stream()
                .map(subStat->SubStatWithSubStatInfoDto.builder()
                        .id(subStat.getId())
                        .name(subStat.getName())
                        .subStatInfos(subStatInfoGroupBySubStat.getOrDefault(subStat.getId(), List.of()))
                        .build())
                .toList();
                
        assertThat(this.subStatService.getAllSubStatsWithSubStatInfo()).isEqualTo(expected);
    }

}
