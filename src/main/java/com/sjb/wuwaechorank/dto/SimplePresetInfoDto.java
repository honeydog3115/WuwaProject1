package com.sjb.wuwaechorank.dto;

import lombok.Builder;

@Builder
public record SimplePresetInfoDto(
    int id,
    String name,
    boolean bookmark
) {}
