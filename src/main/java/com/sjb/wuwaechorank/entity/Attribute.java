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
public class Attribute{
    @PrimaryKey
    @Builder.Default
    private int id=1;
    @Builder.Default
    private String name= "용융";
    @Builder.Default
    private String imagePath="asdf/qewr/a.jpg";
}