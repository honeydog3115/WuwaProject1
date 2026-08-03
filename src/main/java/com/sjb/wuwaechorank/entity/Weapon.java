package com.sjb.wuwaechorank.entity;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;

public class Weapon {
    @PrimaryKey
    private int id;
    private String name;
    private String imagePath;

    public Weapon(){

    }

    public Weapon(int id, String name, String imagePath){
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
