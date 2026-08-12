package com.sjb.wuwaechorank.dto;

import java.util.List;

public record PresetInfoDto(
    String name,
    int resonatorId,
    double score,
    List<ResonatorEchoInfoDto> echosInfo
) {}
