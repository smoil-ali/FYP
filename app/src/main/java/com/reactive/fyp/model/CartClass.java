package com.reactive.fyp.model;

import java.io.Serializable;
import java.util.List;

public class CartClass implements Serializable {
    String id;
    List<ImageClass> list;
    String total;
    String address;
    String timestamp;
    boolean status;

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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CartClass{" +
                "id='" + id + '\'' +
                ", list=" + list +
                ", total='" + total + '\'' +
                ", address='" + address + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", status=" + status +
                '}';
    }
}
