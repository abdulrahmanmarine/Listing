package com.example.listing.Utils;


import com.example.listing.models.User;

public class Loginsession {

    private static Loginsession instance;
    private User user;
    private String token;


    private Loginsession( String token, User user) {
        this.token = token;
        this.user = user;
    }

    public static void initializer( String token, User user) {
        if (instance == null) {
            synchronized (Loginsession.class) {
                instance = new Loginsession(token, user);
            }
        }
    }

    public static Loginsession getInstance() {
        if (instance == null) {
            return null;
        } else {

            return instance;
        }

    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}





