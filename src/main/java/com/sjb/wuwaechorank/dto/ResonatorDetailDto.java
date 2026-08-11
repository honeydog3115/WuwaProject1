package com.sjb.wuwaechorank.dto;

import java.util.List;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.Weapon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// 공명자 세부 정보 전달 클래스
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResonatorDetailDto {
    int id; 
    String name;
    Attribute attribute;
    Weapon weapon;
    int star;
    // 최대 길이 6
    List<String> validStats;
    String energyRegenRequirements;
    String imagePath;
}
