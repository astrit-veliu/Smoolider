package com.av.smoothslider;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_Gift {
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("name")
    @Expose
    private Integer name;
    @SerializedName("description")
    @Expose
    private Integer description;
    @SerializedName("url")
    @Expose
    private Integer url;
    @SerializedName("image")
    @Expose
    private Integer image;

    public Integer getUrl() {
        return url;
    }

    public void setUrl(Integer url) {
        this.url = url;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }


    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }
}
