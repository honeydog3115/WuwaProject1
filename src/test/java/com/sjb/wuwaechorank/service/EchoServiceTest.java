package com.sjb.wuwaechorank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.sonataeffect.SonataEffectDao;
import com.sjb.wuwaechorank.dto.EchoInfoGroupBySonataEffectDto;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.SonataEffect;
import com.sjb.wuwaechorank.service.echo.EchoService;
import com.sjb.wuwaechorank.service.echo.EchoServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EchoServiceTest {
    @Mock
    EchoDao echoDao;

    @Mock
    SonataEffectDao sonataEffectDao;

    @InjectMocks
    EchoService echoService = new EchoServiceImpl(echoDao, sonataEffectDao);

    Echo echo1;
    Echo echo2;
    Echo echo3;
    Echo echo4;

    SonataEffect sonataEffect1; 
    SonataEffect sonataEffect2; 

    @BeforeEach
    void setUp(){
        echo1 = new Echo(1, "꾹꾹복어", 1, "1COST", "asdf/qwer/a.jpg");
        echo2 = new Echo(2, "타종거북이", 1, "4COST", "asdf/qwer/b.jpg");
        echo3 = new Echo(3, "화살곰", 1, "3COST", "asdf/qwer/c.jpg");
        echo4 = new Echo(4, "지옥불기사", 2, "4COST", "asdf/qwer/d.jpg");
        sonataEffect1 = new SonataEffect(1, "야밤의 서리", "asdf/qwer/a.jpg");
        sonataEffect2 = new SonataEffect(2, "솟구치는 용암", "asdf/qwer/b.jpg");
    }

    @Test
    void getAllEchos(){
        List<Echo> echos = new ArrayList<>();
        echos.add(echo1);
        echos.add(echo2);
        echos.add(echo3);
        echos.add(echo4);

        List<SonataEffect> sonataEffects = List.of(sonataEffect1, sonataEffect2);

        when(this.echoDao.getAll()).thenReturn(echos);
        when(this.sonataEffectDao.getAll()).thenReturn(sonataEffects);

        Map<Integer, List<Echo>> echosBySonataEffectMap = echos.stream()
                .collect(Collectors.groupingBy(Echo::getSonataEffectId));

        List<EchoInfoGroupBySonataEffectDto> expected = sonataEffects.stream()
                .map(sonataEffect -> EchoInfoGroupBySonataEffectDto.builder()
                        .id(sonataEffect.getId())
                        .name(sonataEffect.getName())
                        .imagePath(sonataEffect.getImagePath())
                        .echos(echosBySonataEffectMap.getOrDefault(sonataEffect.getId(), List.of()))
                        .build())
                .toList();

        assertThat(this.echoService.getAllEchos()).isEqualTo(expected);
    }
}
