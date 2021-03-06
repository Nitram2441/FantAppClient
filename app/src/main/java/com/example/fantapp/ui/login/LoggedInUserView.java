package com.example.fantapp.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private String token;
    //... other data fields that may be accessible to the UI

    //might change this to token as mikael did
    LoggedInUserView(String token) {

        this.token = token;
    }


    String getToken(){
        return token;
    }

    String getDisplayName() {
        return displayName;
    }
}