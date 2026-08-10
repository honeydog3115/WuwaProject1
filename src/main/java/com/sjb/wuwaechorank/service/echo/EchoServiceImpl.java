package com.sjb.wuwaechorank.service.echo;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sjb.wuwaechorank.dao.entity.echo.EchoDao;
import com.sjb.wuwaechorank.dao.entity.sonataeffect.SonataEffectDao;
import com.sjb.wuwaechorank.dto.EchoInfoGroupBySonataEffectDto;
import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.SonataEffect;

@Service
public class EchoServiceImpl implements EchoService{
    private EchoDao echoDao;
    private SonataEffectDao sonataEffectDao;

    public EchoServiceImpl(EchoDao echoDao, SonataEffectDao sonataEffectDao){
        this.echoDao = echoDao;
        this.sonataEffectDao = sonataEffectDao;
    }

    @Override
    public List<EchoInfoGroupBySonataEffectDto> getAllEchos() {
        List<SonataEffect> sonataEffects = this.sonataEffectDao.getAll();

        List<Echo> echos = this.echoDao.getAll();
        Map<Integer, List<Echo>> echosBySonataEffectMap = echos.stream()
                .collect(Collectors.groupingBy(Echo::getSonataEffectId));

        return sonataEffects.stream()
                .map(sonataEffect -> EchoInfoGroupBySonataEffectDto.builder()
                        .id(sonataEffect.getId())
                        .name(sonataEffect.getName())
                        .imagePath(sonataEffect.getImagePath())
                        .echos(echosBySonataEffectMap.getOrDefault(sonataEffect.getId(), List.of()))
                        .build())
                .toList();
    }

    @Override
    public List<SonataEffect> getEchoFilter() {
        return this.sonataEffectDao.getAll();
    }
    
}