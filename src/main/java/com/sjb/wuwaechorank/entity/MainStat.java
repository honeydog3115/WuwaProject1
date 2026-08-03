package com.sjb.wuwaechorank.entity;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 주음속성
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainStat {
    @PrimaryKey
    private int id;
    private String name;
    private String value;
    private String imagePath;
}
