package com.example.healthpredict.network;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("message")
    public String message;

    @SerializedName("user")
    public UserData user;

    @SerializedName("access")
    public String access;

    @SerializedName("refresh")
    public String refresh;

    public static class UserData {
        public int id;
        public String username;
        public String email;
    }
}
