package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.Echo;

import lombok.Builder;

// 화음 이펙트로 그룹화한 에코 정보 DTO
@Builder
public record EchoInfoGroupBySonataEffectDto(
    int id,
    String name,
    String imagePath,
    List<Echo> echos
){}
