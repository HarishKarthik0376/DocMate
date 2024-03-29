package com.example.doctormate.models;

public class bmimodels {
    public bmimodels() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String type;
    public String value;
    public String status;
    public String date;
    public String time;

    public bmimodels(String type, String value, String status, String date, String time) {
        this.type = type;
        this.value = value;
        this.status = status;
        this.date = date;
        this.time = time;
    }


}
