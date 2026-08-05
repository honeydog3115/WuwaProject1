package com.sjb.wuwaechorank.entity;

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
public class SonataEffect {
    @PrimaryKey
    @Builder.Default
    private int id = 1;
    @Builder.Default
    private String name = "야밤의 서리";
    @Builder.Default
    private String imagePath = "asdf/qwer/a.jpg";
}
