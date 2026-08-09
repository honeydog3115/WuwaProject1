package com.sjb.wuwaechorank.dto;

import com.sjb.wuwaechorank.entity.Attribute;
import com.sjb.wuwaechorank.entity.ValidStat;
import com.sjb.wuwaechorank.entity.Weapon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResonatorInfoDto {
    int id;
    Attribute attribute;
    Weapon weapon;
    int star;
    int validateStatId;
    String imagePath;
}
