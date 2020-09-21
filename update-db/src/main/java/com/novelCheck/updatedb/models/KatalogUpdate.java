package com.novelCheck.updatedb.models;

import javax.persistence.*;

@Entity
@Table(name = "novelList")//,catalog = ???
public class KatalogUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    private String userName;
    @Column(name = "novelID")
    private String novelID;
    @Column(name = "strona")
    private String strona;//TODO zamienić na enum albo zrobić db

    public KatalogUpdate(String novelID, String strona) {
        this.novelID = novelID;
        this.strona = strona;
    }

    public KatalogUpdate() {
    }

    public KatalogUpdate(String novelID) {
        this.novelID = novelID;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
