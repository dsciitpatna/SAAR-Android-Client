package com.example.saar.Retrofit;

import com.example.saar.Timeline_Events.Event;
import com.example.saar.Gallery.Gallery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Class with different networking requests using Retrofit

public interface GetDataService {
    @GET("/assets/timeline_sample.json")
    Call<List<Event>> getAllEvents();

    @GET("/assets/gallery_sample.json")
    Call<List<Gallery>> getAllPhotos();
}
