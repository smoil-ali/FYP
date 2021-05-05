package com.reactive.fyp.model;

import java.io.Serializable;

public class ProductModel implements Serializable {
    String uid;
    String image;
    String category;
    String catUid;

    public ProductModel() {
    }

    public ProductModel(String uid, String image, String category ,String catUid) {
        this.uid = uid;
        this.image = image;
        this.category = category;
        this.catUid=catUid;
    }

    public String getCatUid() {
        return catUid;
    }

    public void setCatUid(String catUid) {
        this.catUid = catUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
