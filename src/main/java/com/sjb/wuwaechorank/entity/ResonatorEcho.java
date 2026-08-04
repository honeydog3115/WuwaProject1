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

public class ResonatorEcho {
    @PrimaryKey
    int id;
    int echoId;
    int mainStatId;
    int SubStatId;
    float score;
}
