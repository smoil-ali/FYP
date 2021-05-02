package com.reactive.fyp.model;

import com.google.android.gms.common.internal.StringResourceValueReader;

import java.io.Serializable;

public class fontClass implements Serializable {
    String id;
    String name;
    String price;

    public fontClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
