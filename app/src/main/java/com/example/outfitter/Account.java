package com.example.outfitter;

public class Account {
    private String mUsername;
    private String mPassword;

    public Account(){

    }

    public Account(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {return mPassword;}

}
