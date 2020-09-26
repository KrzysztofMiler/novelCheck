package com.novelCheck.CheckMVC.models;


import java.util.ArrayList;
import java.util.List;


public class UserUser {

    private Long ID;


    private String userName;


    private String email;


    private List<KatalogUpdate> subNovel ;

    public UserUser(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public void subToNovel(KatalogUpdate katalogUpdate){
        subNovel.add(katalogUpdate);
    }

    public List<KatalogUpdate> getSubNovel() {
        return subNovel;
    }

    public UserUser(String userName, String email, List<KatalogUpdate> subNovel) {
        this.userName = userName;
        this.email = email;
        this.subNovel = subNovel;
    }

    public UserUser() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
