package com.example.papacalls;

public class Contacts
{
String name, image, status,uid;
int y=0;
    public Contacts() {
    }



    public Contacts(String name, String status, String uid) {
        this.name = name;
        this.status = status;
        this.uid = uid;
        this.y = y;

    }

    public int gety() {
        return y;
    }

    public void sety(int y) {
        this.y = y;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



}
