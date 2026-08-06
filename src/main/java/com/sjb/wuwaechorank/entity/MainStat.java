package com.sjb.wuwaechorank.entity;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 주음속성
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainStat {
    @PrimaryKey
    @Builder.Default
    private int id = 1;
    @Builder.Default
    private String name = "공격력";
    @Builder.Default
    private String value = "12.5%";
    @Builder.Default
    private String imagePath = "asdf/qwer/a.jpg";
}