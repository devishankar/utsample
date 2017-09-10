package com.github.devishankar.utsample;

/**
 * Created by Devishankar on 9/10/2017.
 */

public class ContactModel {
    private int id;
    private String name;
    private String phone;

    public ContactModel(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
