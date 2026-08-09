package com.sjb.wuwaechorank.entity;

import com.sjb.wuwaechorank.customannotation.ForeignKey;
import com.sjb.wuwaechorank.customannotation.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ValidStat {
    @PrimaryKey
    @Builder.Default
    int id = 1;
    @ForeignKey
    @Builder.Default
    int stat1 = 1;
    @ForeignKey
    @Builder.Default
    int stat2 = 1;
    @ForeignKey
    @Builder.Default
    int stat3 = 1;
    @ForeignKey
    @Builder.Default
    int stat4 = 1;
    @ForeignKey
    @Builder.Default
    int stat5 = 1;
}
