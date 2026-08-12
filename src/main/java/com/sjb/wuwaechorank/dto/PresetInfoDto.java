package com.sjb.wuwaechorank.dto;

import java.util.List;

// PresetService.savePreset()에 사용될 DTO
public record PresetInfoDto(
    int userId,
    String name,
    int resonatorId,
    double score,
    List<ResonatorEchoInfoDto> echosInfo
) {}
