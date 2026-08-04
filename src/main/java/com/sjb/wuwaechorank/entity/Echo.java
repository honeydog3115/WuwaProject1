package com.sjb.wuwaechorank.entity;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Echo {
    @PrimaryKey
    int id;
    String name;
    int SonataEffectId;
    String cost;
    String ImagePath;
}
