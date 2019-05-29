package com.example.saar.Gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.saar.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Gallery> photoList;

    public GalleryAdapter(List<Gallery> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public GalleryAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_list_row, parent, false);

        return new GalleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.GalleryViewHolder galleryViewHolder, int position) {
        String photo_url = photoList.get(position).getImage_url();
        Picasso.get()
                .load(photo_url)
                .placeholder(R.drawable.placeholder_image)
                .into(galleryViewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public GalleryViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageview_gallery);
        }
    }
}
