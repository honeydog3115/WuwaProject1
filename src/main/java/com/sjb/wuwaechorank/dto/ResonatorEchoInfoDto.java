package com.sjb.wuwaechorank.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ResonatorEchoInfoDto(
    int echoId,
    // 최대 5개
    int mainStatId,
    List<ResonatorEchoSubStatDto> echoSubStats
) {}
