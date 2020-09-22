package com.novelCheck.CheckMVC.models;

public class KatalogNovel {
    private String chapNum;
    private String chapLink;
    private String tytul;

    public KatalogNovel(String chapNum, String chapLink, String tytul) {
        this.chapNum = chapNum;
        this.chapLink = chapLink;
        this.tytul = tytul;
    }

    public KatalogNovel() {
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
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
