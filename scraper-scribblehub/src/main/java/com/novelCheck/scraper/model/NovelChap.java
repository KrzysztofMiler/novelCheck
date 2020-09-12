package com.novelCheck.scraper.model;

public class NovelChap {

    private String chapNum;
    private String chapLink;
    private String chapName;//TODO zaaplikować w poprzednim scrapie

    public NovelChap(String chapNum, String chapLink, String chapName) {
        this.chapNum = chapNum;
        this.chapLink = chapLink;
        this.chapName = chapName;
    }

    public NovelChap(String chapNum, String chapLink) {
        this.chapNum = chapNum;
        this.chapLink = chapLink;
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

    public String getChapName() {
        return chapName;
    }

    public void setChapName(String chapName) {
        this.chapName = chapName;
    }
}
