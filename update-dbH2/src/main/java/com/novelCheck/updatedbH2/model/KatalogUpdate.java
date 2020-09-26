package com.novelCheck.updatedbH2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KATALOGUPDATE")
public class KatalogUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    @Column(name = "NOVELID")
    private String novelID;

    @Column(name = "STRONA")
    private String strona;

    @ManyToMany(mappedBy = "subNovel")
    private List<UserUser> subskrybenci = new ArrayList<>();

//    @Column(name="SETUSER")
//    @ManyToMany(mappedBy = "novelList")
//    private Set<UserUser> setUser;

    public void addSubskrybent(UserUser user){
        subskrybenci.add(user);
    }

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
