package com.novelCheck.requestupdate.models;

public class KatalogNovel {
    private String chapNum;
    private String chapLink;

    public KatalogNovel(String chapNum, String chapLink) {
        this.chapNum = chapNum;
        this.chapLink = chapLink;
    }

    public KatalogNovel() {
    }

    public String getChapNum() {
        return chapNum;
    }

    public void setChapNum(String chapNum) {
        this.chapNum = chapNum;
    }

    public String getChapLink() {
        return chapLink;
    }

    public void setChapLink(String chapLink) {
        this.chapLink = chapLink;
    }
}
