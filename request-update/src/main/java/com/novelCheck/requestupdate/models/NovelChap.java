package com.novelCheck.requestupdate.models;

public class NovelChap {

    private String chapNum;
    private String chapLink;

    public NovelChap(String chapNum, String chapLink) {
        this.chapNum = chapNum;
        this.chapLink = chapLink;
    }

    public NovelChap() {
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
