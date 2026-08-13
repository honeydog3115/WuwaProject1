package com.sjb.wuwaechorank.dto;

import lombok.Builder;

@Builder
public record SubStatDetailDto(
    String subStatName,
    String subStatValue,
    String subStatChance
) {}
