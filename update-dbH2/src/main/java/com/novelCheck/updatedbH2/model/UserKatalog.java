package com.novelCheck.updatedbH2.model;

import java.util.List;

public class UserKatalog {
    private List<KatalogUpdate> novels;
    //private KatalogUpdate novels;
    private String strona;

    public UserKatalog(List<KatalogUpdate> novels, String strona) {
        this.novels = (List<KatalogUpdate>) novels;
        this.strona = strona;
    }

    /*public String getStrona() {
        return strona;
    }

    public void setStrona(String strona) {
        this.strona = strona;
    }*/

    public UserKatalog() {
    }


    public List<KatalogUpdate> getNovels() {
        return (List<KatalogUpdate>) novels;
    }

    public void setNovels(List<KatalogUpdate> novels) {
        this.novels = (List<KatalogUpdate>) novels;
    }
}
