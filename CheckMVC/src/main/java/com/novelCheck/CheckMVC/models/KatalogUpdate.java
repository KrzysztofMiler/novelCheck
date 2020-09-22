package com.novelCheck.CheckMVC.models;

public class KatalogUpdate {
    private String novelID;
    private String strona;//TODO zamienić na enum albo zrobić db

    public KatalogUpdate(String novelID, String strona) {
        this.novelID = novelID;
        this.strona = strona;
    }
/*
    public KatalogUpdate(String novelID) {
        this.novelID = novelID;
    }*/

    public KatalogUpdate() {
    }

    public String getNovelID() {
        return novelID;
    }

    public void setNovelID(String novelID) {
        this.novelID = novelID;
    }

    public String getStrona() {
        return strona;
    }

    public void setStrona(String strona) {
        this.strona = strona;
    }
}