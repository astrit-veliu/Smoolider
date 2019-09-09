package com.av.smoothslider.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Astrit Veliu on 09,September,2019
 */
public class Model_Smoolider {
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
