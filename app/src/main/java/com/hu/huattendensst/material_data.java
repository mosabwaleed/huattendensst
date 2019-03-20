package com.hu.huattendensst;

public class material_data {
    String name;
    String time;
    String hall;
    String sec;
    String days;
    String doctorid;


    public material_data(String name, String time, String hall, String sec, String days, String doctorid) {
        this.name = name;
        this.time = time;
        this.hall = hall;
        this.sec = sec;
        this.days = days;
        this.doctorid = doctorid;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        this.sec = sec;
    }
}
