package com.reactive.fyp.model;

public class ImageClass {
    String id;
    String image;
    String price;

    public ImageClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        if (image == null)
            return "";
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        if (price == null)
            return "";
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
