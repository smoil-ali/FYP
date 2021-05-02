package com.reactive.fyp.model;

import java.io.Serializable;

public class StickerModel implements Serializable {
    String imageUrl;
    String uid;
    String price;

    public StickerModel(String imageUrl, String uid, String price) {
        this.imageUrl = imageUrl;
        this.uid = uid;
        this.price = price;
    }

    public StickerModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
