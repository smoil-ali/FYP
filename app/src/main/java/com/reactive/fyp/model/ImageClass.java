package com.reactive.fyp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.reactive.fyp.BR;

import java.io.Serializable;

public class ImageClass extends BaseObservable implements Serializable {
    String id;
    String ownerId;
    String image;
    String actualPrice;
    String formattedPrice;
    String price;
    String description;
    String qty;

    public ImageClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getImage() {
        if (image == null)
            return "";
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getPrice() {
        if (price == null)
            return "";
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getDescription() {
        if (description == null)
            return "";
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public String getActualPrice() {
        if (actualPrice == null)
            return "";
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    @Bindable
    public String getQty() {
        if (qty == null)
            return "1";
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
        notifyPropertyChanged(BR.qty);
    }


    @Bindable({"price"})
    public String getFormattedPrice() {
        return getPrice() + "/-Rs";
    }


    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
        notifyPropertyChanged(BR.formattedPrice);
    }


    @Bindable
    public String getOwnerId() {
        if (ownerId == null)
            return "";
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        notifyPropertyChanged(BR.ownerId);
    }

    @Override
    public String toString() {
        return "ImageClass{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", actualPrice='" + actualPrice + '\'' +
                ", formattedPrice='" + formattedPrice + '\'' +
                ", price='" + price + '\'' +
                ", description='" + description + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}
