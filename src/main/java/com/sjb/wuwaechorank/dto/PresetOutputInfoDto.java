package com.sjb.wuwaechorank.dto;

import java.util.List;

import lombok.Builder;

// 사용자가 프리셋을 눌렀을 때 전달해야할 정보
@Builder
public record PresetOutputInfoDto(
    int presetId,
    String presetName,
    ResonatorDetailDto resonatorDetailDto,
    List<EchoDetailDto> echoDetailDtos,
    Double totalScore
) {}
