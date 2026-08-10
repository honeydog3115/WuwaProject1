package com.sjb.wuwaechorank.service.echo;

import java.util.List;

import com.sjb.wuwaechorank.dto.EchoInfoGroupBySonataEffectDto;
import com.sjb.wuwaechorank.entity.SonataEffect;

// 에코 관련 서비스 인터페이스
public interface EchoService {
    List<EchoInfoGroupBySonataEffectDto> getAllEchos();
    List<SonataEffect> getEchoFilter();
}
