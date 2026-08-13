package com.sjb.wuwaechorank.dto;

import lombok.Builder;

@Builder
public record ResonatorEchoSubStatDto(
    int subStatId,
    int subStatInfoId,
    String value,
    String chance
) {}
