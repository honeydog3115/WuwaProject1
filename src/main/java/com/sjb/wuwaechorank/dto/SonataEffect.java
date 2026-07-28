package com.sjb.wuwaechorank.dto;

public class SonataEffect {
    private int id;
    private String name;
    private String imagePath;

    public SonataEffect(){

    }

    public SonataEffect(int id, String name, String imagePath){
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
