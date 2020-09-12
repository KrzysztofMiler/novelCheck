package com.novelCheck.updateList.models;

public class KatalogUpdate {
    private String novelID;

    public KatalogUpdate(String novelID) {
        this.novelID = novelID;
    }

    public String getNovelID() {
        return novelID;
    }

    public void setNovelID(String novelID) {
        this.novelID = novelID;
    }
}
