package com.reactive.fyp.model;

import java.io.Serializable;

public class PostClass implements Serializable {
    String id;
    ImageClass image;
    String publisherId;
    String timestamp;

    public PostClass() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageClass getImage() {
        return image;
    }

    public void setImage(ImageClass image) {
        this.image = image;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PostClass{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", publisherId='" + publisherId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
