package com.sjb.wuwaechorank.dto;

import java.util.List;

import lombok.Builder;

// PresetService.savePreset()에 사용될 DTO
@Builder
public record PresetInputInfoDto(
    int userId,
    String name,
    int resonatorId,
    double score,
    boolean bookmark,
    List<ResonatorEchoInfoDto> echosInfo
) {}
