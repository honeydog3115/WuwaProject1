package com.sjb.wuwaechorank.dto;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.entity.Weapon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 전체 공명자 정보 클래스
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResonatorsInfoDto {
    int id;
    String name;
    Attribute attribute;
    Weapon weapon;
    int star;
    String imagePath;
}
