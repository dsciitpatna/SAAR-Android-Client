package com.example.saar.Timeline_Events;

import com.google.gson.annotations.SerializedName;

//POJO Java class representing Event of Timeline
//Parameters matches keys in json
public class Event {

    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("Description")
    private String Description;
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("location")
    private String location;
    @SerializedName("image_url")
    private String image_url;

    public Event(Integer id, String title, String Desc, String date,
                 String time, String location, String image_url) {
        this.id = id;
        this.title = title;
        this.Description = Desc;
        this.date = date;
        this.time = time;
        this.location = location;
        this.image_url = image_url;
    }

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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
