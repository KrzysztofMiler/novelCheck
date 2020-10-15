package com.novelCheck.CheckMVC.resources;

public class NullUserKatalog extends Exception {
    public NullUserKatalog(String userKatalog_jest_null) {
        super(userKatalog_jest_null);
    }
}
