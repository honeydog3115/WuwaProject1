package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.SubStatInfo;

import lombok.Builder;

@Builder
public record SubStatWithSubStatInfoDto(
    int id,
    String name,
    List<SubStatInfo> subStatInfos
) {}
