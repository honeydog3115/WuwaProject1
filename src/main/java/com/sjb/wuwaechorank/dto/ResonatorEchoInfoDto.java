package com.sjb.wuwaechorank.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ResonatorEchoInfoDto(
    // 최대 5개
    List<ResonatorEchoSubStatDto> echoSubStats
) {}
