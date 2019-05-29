package com.example.saar.Timeline_Events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saar.R;
import com.squareup.picasso.Picasso;

public class EventDetails extends AppCompatActivity {
    ImageView event_image;
    TextView title, description, location, date, time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getSupportActionBar().setTitle(getResources().getString(R.string.event_details));
        event_image = findViewById(R.id.event_image);
        title = findViewById(R.id.event_title);
        location = findViewById(R.id.event_location);
        description = findViewById(R.id.event_description);
        date = findViewById(R.id.event_date);
        time = findViewById(R.id.event_time);

        //setting back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        String title = getIntent().getStringExtra("title");
        this.title.setText(title);
        String description = getIntent().getStringExtra("description");
        this.description.setText(description);
        String location = getIntent().getStringExtra("location");
        this.location.setText(location);
        String date = getIntent().getStringExtra("date");
        this.date.setText(date);
        String time = getIntent().getStringExtra("time");
        this.time.setText(time);
        String image_url = getIntent().getStringExtra("image_url");
        Picasso.get()
                .load(image_url)
                .placeholder(R.drawable.placeholder_image)
                .into(event_image);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //handle back button action
        onBackPressed();
        return true;
    }
}
