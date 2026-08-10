package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Weapon;

import lombok.Builder;

// 공명자 선택창에서 필요한 공명자 필터링 정보 DTO
@Builder
public record ResonatorFilterDto (
    List<Attribute> attributes,
    List<Weapon> weapons
) {}
