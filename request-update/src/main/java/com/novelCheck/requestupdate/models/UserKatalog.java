package com.novelCheck.requestupdate.models;

import java.util.List;

public class UserKatalog {
    private List<KatalogUpdate> novels;
    private String strona;

    public UserKatalog(List<KatalogUpdate> novels, String strona) {
        this.novels = novels;
        this.strona = strona;
    }
    public UserKatalog() {
    }

    public List<KatalogUpdate> getNovels() {
        return novels;
    }

    public void setNovels(List<KatalogUpdate> novels) {
        this.novels = novels;
    }
}
