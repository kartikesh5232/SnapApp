package com.example.whatsappclone;

public class UserObject {
    String name, phone, uid;

    public UserObject(String uid, String name, String phone) {
        this.uid = uid;
        this.phone = phone;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
