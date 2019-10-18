package com.example.outfitter;

public static class Account {
    public String username;
    public String password;

    public Account(String u, String p) {
        username = u;
        password = p;
    }

    public void setUsername(String u) {
        username = u;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}

}
