package com.novelCheck.updatedbH2.model;

import java.util.List;

public class UserList {
    private List<UserUser> userUser;

    private List<String> novelID;

    public UserList(List<UserUser> userUser, List<String> novelID) {
        this.userUser = userUser;
        this.novelID = novelID;
    }

    public List<String> getNovelID() {
        return novelID;
    }

    public void setNovelID(List<String> novelID) {
        this.novelID = novelID;
    }

    public List<UserUser> getUserUser() {
        return userUser;
    }

    public void setUserUser(List<UserUser> userUser) {
        this.userUser = (List<UserUser>) userUser;
    }


    public UserList(List<UserUser> userUser) {
        this.userUser = userUser;
    }

    public UserList() {
    }

}
