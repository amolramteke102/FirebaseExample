package com.encureit.firebaseexample;

/**
 * Created by root on 25/5/17.
 */

public class users {
    public String name;
    public String value;

    public users(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public users() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
