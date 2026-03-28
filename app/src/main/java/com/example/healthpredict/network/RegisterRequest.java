package com.example.healthpredict.network;

public class RegisterRequest {
    public String username;
    public String email;
    public String password;
    public String specialty;
    public String hospital;
    public String full_name;

    public RegisterRequest(String username, String email, String password, String specialty, String hospital,
            String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.hospital = hospital;
        this.full_name = fullName;
    }
}
