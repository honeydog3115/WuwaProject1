package com.sjb.wuwaechorank.service.echo;

import java.util.ArrayList;
import java.util.List;

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
        
        List<EchoInfoGroupBySonataEffectDto> dto = new ArrayList<>();

        for (SonataEffect sonataEffect : sonataEffects) {
            List<Echo> echos = this.echoDao.getAllBySonataEffect(sonataEffect.getId());
            EchoInfoGroupBySonataEffectDto element = EchoInfoGroupBySonataEffectDto.builder()
                    .id(sonataEffect.getId())
                    .name(sonataEffect.getName())
                    .imagePath(sonataEffect.getImagePath())
                    .echos(echos)
                    .build();

            dto.add(element);
        }

        return dto;
    }
    
}