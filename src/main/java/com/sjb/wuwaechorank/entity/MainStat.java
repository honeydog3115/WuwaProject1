package com.sjb.wuwaechorank.entity;

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
    private int id;
    private String name;
    private String value;
    private String imagePath;
}
