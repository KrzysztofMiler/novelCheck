package com.novelCheck.novelMail.model;

import java.util.List;

public class UserKatalogWrapper {
    //private UserUser userUser;
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


}
