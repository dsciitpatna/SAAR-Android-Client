package com.example.saar.Gallery;

import com.google.gson.annotations.SerializedName;

public class Gallery {
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("status")
    private String status;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("hover_text")
    private String hover_text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getHover_text() {
        return hover_text;
    }

    public void setHover_text(String hover_text) {
        this.hover_text = hover_text;
    }
}
