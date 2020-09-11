package com.novelCheck.requestupdate.models;

public class KatalogUpdate {
    private String novelID;
    //private int ID;

    public KatalogUpdate() {
    }

    public KatalogUpdate(String novelID) {
        this.novelID = novelID;
        //this.ID = ID;
    }

    public String getNovelID() {
        return novelID;
    }

    public void setNovelID(String novelID) {
        this.novelID = novelID;
    }
}
