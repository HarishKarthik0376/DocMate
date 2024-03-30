package com.example.doctormate.models;

public class remindermodel {
    public String type;


    public remindermodel() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String description;
    public String time;

    public remindermodel(String type, String description, String time, String uuid) {
        this.type = type;
        this.description = description;
        this.time = time;
        this.uuid = uuid;
    }

    public String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public remindermodel(String type, String description, String time) {
        this.type = type;
        this.description = description;
        this.time = time;
    }

}