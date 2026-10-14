package com.sjb.wuwaechorank.dto.request;

import java.util.List;

import com.sjb.wuwaechorank.dto.ResonatorEchoInfoDto;

public record ResonatorEchoScoreRequest(
    int resonatorId,
    List<ResonatorEchoInfoDto> resonatorEchoInfoDtos,
    boolean insertDB,
    int presetId
) {}
