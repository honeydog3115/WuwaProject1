package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.Echo;
import com.sjb.wuwaechorank.entity.MainStat;

import lombok.Builder;

@Builder
public record EchoDetailDto(
    Echo echo,
    MainStat mainstat,
    List<SubStatDetailDto> subStatDetailDtos
) {}
