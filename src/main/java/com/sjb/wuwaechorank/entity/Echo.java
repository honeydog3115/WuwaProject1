package com.sjb.wuwaechorank.entity;

import java.util.List;

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
public class Echo {
    @PrimaryKey
    @Builder.Default
    int id = 1;
    @Builder.Default
    String name = "꾹꾹복어";
    @ForeignKey
    @Builder.Default
    List<Integer> sonataEffectId = List.of(1);
    @Builder.Default
    String cost = "1COST";
    @Builder.Default
    String imagePath = "asdf/qwer/a.jpg";
}
