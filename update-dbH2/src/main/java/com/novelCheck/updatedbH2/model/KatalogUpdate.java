package com.novelCheck.updatedbH2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class KatalogUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String novelID;
    private String strona;


    public KatalogUpdate() {
    }

    public KatalogUpdate(String novelID, String strona) {
        this.novelID = novelID;
        this.strona = strona;
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


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
