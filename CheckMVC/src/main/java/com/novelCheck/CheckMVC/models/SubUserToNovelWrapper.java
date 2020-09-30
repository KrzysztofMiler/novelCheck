package com.novelCheck.CheckMVC.models;

public class SubUserToNovelWrapper {
    private String userName;

    private String novelID;

    public SubUserToNovelWrapper() {
    }

    public SubUserToNovelWrapper(String userName, String novelID) {
        this.userName = userName;
        this.novelID = novelID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNovelID() {
        return novelID;
    }

    public void setNovelID(String novelID) {
        this.novelID = novelID;
    }
}
