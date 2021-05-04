package com.reactive.fyp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.reactive.fyp.BR;

import java.io.Serializable;
import java.util.List;

public class CartClass extends BaseObservable implements Serializable {
    String id;
    List<ImageClass> list;
    String total;
    String formattedPrice;
    String address;
    String timestamp;
    String phone;
    String postal;
    boolean status;
    boolean displayError;

    public CartClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ImageClass> getList() {
        return list;
    }

    public void setList(List<ImageClass> list) {
        this.list = list;
    }

    @Bindable
    public String getTotal() {
        if (total == null)
            return "";
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }

    @Bindable
    public String getAddress() {
        if (address == null)
            return "";
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable({"displayError","address"})
    public String getAddressError(){
        if (!isDisplayError()){
            return "";
        }

        if (getAddress().isEmpty()){
            return "Address Field is Empty";
        }
        return "";
    }

    @Bindable
    public String getTimestamp() {
        if (timestamp == null)
            return "";
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        notifyPropertyChanged(BR.timestamp);
    }

    @Bindable
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }

    @Bindable({"total"})
    public String getFormattedPrice() {
        return getTotal() + "/-Rs";
    }


    public void setFormattedPrice(String formattedPrice) {
        this.formattedPrice = formattedPrice;
        notifyPropertyChanged(BR.formattedPrice);
    }

    @Bindable
    public String getPhone() {
        if (phone ==  null)
            return "";
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable({"displayError","phone"})
    public String getPhoneError(){
        if (!isDisplayError()){
            return "";
        }

        if (getPhone().isEmpty()){
            return "Phone Field is Empty";
        }
        return "";
    }

    @Bindable
    public String getPostal() {
        if (postal == null)
            return "";
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
        notifyPropertyChanged(BR.postal);
    }

    @Bindable({"displayError","postal"})
    public String getPostalError(){
        if (!isDisplayError()){
            return "";
        }

        if (getPostal().isEmpty()){
            return "Postal Field is Empty";
        }
        return "";
    }

    @Bindable
    public boolean isDisplayError(){
        return displayError;
    }

    public void setDisplayError(boolean displayError) {
        this.displayError = displayError;
        notifyPropertyChanged(BR.displayError);
    }

    @Override
    public String toString() {
        return "CartClass{" +
                "id='" + id + '\'' +
                ", list=" + list +
                ", total='" + total + '\'' +
                ", formattedPrice='" + formattedPrice + '\'' +
                ", address='" + address + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", phone='" + phone + '\'' +
                ", postal='" + postal + '\'' +
                ", status=" + status +
                '}';
    }
}
