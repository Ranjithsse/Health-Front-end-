package com.example.healthpredict.network;

public class RegisterRequest {
    public String username;
    public String email;
    public String password;
    public String specialty;
    public String hospital;

    public RegisterRequest(String username, String email, String password, String specialty, String hospital) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.hospital = hospital;
    }
}
