package com.novelCheck.novelMail.model;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<UserUser> userUser;

    public List<UserUser> getUserUser() {
        return userUser;
    }

    public void setUserUser(List<UserUser> userUser) {
        this.userUser = userUser;
    }
    public UserList(List<UserUser> userUser) {
    }

    public UserList() {
        userUser = new ArrayList<>();//tutaj chba magia jest
    }

}
