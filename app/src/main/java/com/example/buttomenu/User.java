package com.example.buttomenu;

import com.google.android.material.textfield.TextInputEditText;

public class User {
    private TextInputEditText name;
    private TextInputEditText email;
    private TextInputEditText username;
    private TextInputEditText phone;

    public void setPhone(TextInputEditText phone) {
        this.phone = phone;
    }

    public void setUsername(TextInputEditText username) {
        this.username = username;
    }

    public void setEmail(TextInputEditText email) {
        this.email = email;
    }

    public void setName(TextInputEditText name) {
        this.name = name;
    }

    public TextInputEditText getPhone() {
        return phone;
    }

    public TextInputEditText getUsername() {
        return username;
    }

    public TextInputEditText getEmail() {
        return email;
    }

    public TextInputEditText getName() {
        return name;
    }

    public User(TextInputEditText name, TextInputEditText email, TextInputEditText username, TextInputEditText phone) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.phone = phone;
    }

    public User(TextInputEditText name) {
        this.name = name;
    }

    public User() {
    }
}
