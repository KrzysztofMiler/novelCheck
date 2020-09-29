package com.novelCheck.CheckMVC.models;

import java.util.List;

public class UserKatalogWrapper {
    private List<KatalogUpdate> subNovel ;

    private String userName;


    private String email;

    public List<KatalogUpdate> getsubNovel() {
        return subNovel;
    }

    public void setsubNovel(List<KatalogUpdate> katalogUpdate) {
        this.subNovel = katalogUpdate;
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

    public UserKatalogWrapper() {
    }

    public UserKatalogWrapper(List<KatalogUpdate> katalogUpdate, String userName, String email) {
        this.subNovel = katalogUpdate;
        this.userName = userName;
        this.email = email;
    }

//
//
//    private UserUser userUser;
//    private  List<KatalogUpdate> katalogUpdate ;
//
//    public UserKatalogWrapper(UserUser userUser, List<KatalogUpdate> katalogUpdate) {
//        this.userUser = userUser;
//        this.katalogUpdate = katalogUpdate;
//    }
//
//    public UserKatalogWrapper(List<KatalogUpdate> katalogUpdate) {
//        this.katalogUpdate = katalogUpdate;
//    }
//
//    public UserKatalogWrapper(UserUser userUser) {
//        this.userUser = userUser;
//    }
//
//    public UserKatalogWrapper() {
//
//    }
//
//    public UserUser getUserUser() {
//        return userUser;
//    }
//
//    public void setUserUser(UserUser userUser) {
//        this.userUser = userUser;
//    }
//
//    public List<KatalogUpdate> getKatalogUpdate() {
//        return katalogUpdate;
//    }
//
//    public void setKatalogUpdate(List<KatalogUpdate> katalogUpdate) {
//        this.katalogUpdate = katalogUpdate;
//    }
}
