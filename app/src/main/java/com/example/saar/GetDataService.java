package com.example.saar;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

//Class with different networking requests using Retrofit

public interface GetDataService {
    @GET("/timeline_sample.json")
    Call<List<Event>> getAllEvents();
    @GET("/gallery_sample.json")
    Call<List<Gallery>> getAllPhotos();
}
