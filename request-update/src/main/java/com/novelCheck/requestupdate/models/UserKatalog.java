package com.novelCheck.requestupdate.models;

import java.util.List;

public class UserKatalog {
    private List<KatalogUpdate> novels;

    public UserKatalog() {
    }

    public List<KatalogUpdate> getNovels() {
        return novels;
    }

    public void setNovels(List<KatalogUpdate> novels) {
        this.novels = novels;
    }
}
