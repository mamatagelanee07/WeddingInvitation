package com.andigeeky.weddinginvitation.view.vo;

public class Credentials {
    private String email;
    private String password;
    private String cPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCPassword() {
        return cPassword;
    }

    public void setCPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cPassword='" + cPassword + '\'' +
                '}';
    }
}
