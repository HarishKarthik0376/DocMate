package com.example.doctormate.models;

public class Users {
    public Users(String fname, String lname, String email, String mobile, String password, String role) {
        Fname = fname;
        this.lname = lname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.role = role;
    }
    public Users() {
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    String Fname;

    public Users(String fname, String lname, String email, String mobile, String password, String role, String ranuid) {
        Fname = fname;
        this.lname = lname;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.role = role;
        this.ranuid = ranuid;
    }

    String lname;
    String email;
    String mobile;
    String password;
    String role;

    public String getRanuid() {
        return ranuid;
    }

    public void setRanuid(String ranuid) {
        this.ranuid = ranuid;
    }

    String ranuid;
}

