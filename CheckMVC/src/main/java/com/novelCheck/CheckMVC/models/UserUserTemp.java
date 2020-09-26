package com.novelCheck.CheckMVC.models;

public class UserUserTemp {
    String userName;
    String email;
    String subNovel;//ten jest problemem
    public UserUserTemp() {
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

    public String getSubNovel() {
        return subNovel;
    }

    public void setSubNovel(String subNovel) {
        this.subNovel = subNovel;
    }

    public UserUserTemp(String userName, String email, String subNovel) {
    }
}
