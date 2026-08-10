package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.Echo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 화음 이펙트로 그룹화한 에코 정보 DTO
@Builder
public record EchoInfoGroupBySonataEffectDto(
    int id,
    String name,
    String imagePath,
    List<Echo> echos
){}
