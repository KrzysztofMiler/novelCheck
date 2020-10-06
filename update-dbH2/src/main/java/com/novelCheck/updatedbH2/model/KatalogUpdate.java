package com.novelCheck.updatedbH2.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
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
    @Fetch(FetchMode.JOIN)//powinno nie powodoćać bł z lazy join
    private Set<UserUser> subskrybenci = new HashSet<>();

//    @Column(name="SETUSER")
//    @ManyToMany(mappedBy = "novelList")
//    private Set<UserUser> setUser;

//    public Set<UserUser> getSubskrybenci() {
//        return subskrybenci;
//    }

    public void addSubskrybent(UserUser user){
        subskrybenci.add(user);
    }

    public KatalogUpdate() {
    }

    public KatalogUpdate(String novelID, String strona) {
        this.novelID = novelID;
        this.strona = strona;
    }

    public Set<UserUser> zwróćSubskrybenci() {//5iq aby nie brał w get all
        return subskrybenci;
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
