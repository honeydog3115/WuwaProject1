package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Weapon;

import lombok.Builder;

@Builder
public record ResonatorFilterDto (
    List<Attribute> attributes,
    List<Weapon> weapons
) {}
