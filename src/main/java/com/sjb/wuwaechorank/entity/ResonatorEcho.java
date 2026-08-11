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
public class ResonatorEcho {
    @PrimaryKey
    @Builder.Default
    int id = 1;
    @ForeignKey
    @Builder.Default
    int echoId = 1;
    @ForeignKey
    @Builder.Default
    Integer mainStatId = 1;
    @ForeignKey
    @Builder.Default
    Integer  SubStatId1 = 1;
    @ForeignKey
    @Builder.Default
    Integer  SubStatId2 = 1;
    @ForeignKey
    @Builder.Default
    Integer  SubStatId3 = 1;
    @ForeignKey
    @Builder.Default
    Integer  SubStatId4 = 1;
    @ForeignKey
    @Builder.Default
    Integer  SubStatId5 = 1;
    @Builder.Default
    double score = 50;
}
