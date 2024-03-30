package com.example.doctormate.models;

public class contactmodel {
    String ranuid;

    public String getUidofdoc() {
        return uidofdoc;
    }

    public contactmodel(String role, String uidofdoc, String fname, String lname,String ranuid) {
        this.role = role;
        this.uidofdoc = uidofdoc;
        this.fname = fname;
        this.lname = lname;
        this.ranuid =ranuid;
    }

    public void setUidofdoc(String uidofdoc) {
        this.uidofdoc = uidofdoc;
    }

    String uidofdoc;

    public String getRanuid() {
        return ranuid;
    }

    public void setRanuid(String ranuid) {
        this.ranuid = ranuid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

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

    String fname;
    String lname;
    String mobile;
    String email;
    String password;

    public contactmodel(String ranuid, String fname, String lname, String mobile, String email, String password) {
        this.ranuid = ranuid;
        this.fname = fname;
        this.lname = lname;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public contactmodel(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public contactmodel() {
    }

    public String name;
    public String role;
}
