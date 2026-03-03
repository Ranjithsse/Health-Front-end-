package com.example.healthpredict.network;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("message")
    public String message;
    
    @SerializedName("user")
    public UserData user;

    public static class UserData {
        public int id;
        public String username;
        public String email;
    }
}
