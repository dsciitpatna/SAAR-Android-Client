package com.example.saar.Gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saar.Gallery.Gallery;
import com.example.saar.Gallery.GalleryAdapter;
import com.example.saar.R;
import com.example.saar.Retrofit.GetDataService;
import com.example.saar.Retrofit.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private GalleryAdapter mAdapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_gallery);
        progressDialog = new ProgressDialog(rootView.getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Gallery>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<Gallery>>() {
            @Override
            public void onResponse(Call<List<Gallery>> call, Response<List<Gallery>> response) {
                progressDialog.dismiss();
                generateDataList(response.body(), rootView);
            }

            @Override
            public void onFailure(Call<List<Gallery>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(rootView.getContext(), "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void generateDataList(List<Gallery> photoList, View rootView) {
        recyclerView = rootView.findViewById(R.id.recycler_view_gallery);
        mAdapter = new GalleryAdapter(photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.gallery_fragment);
    }
}
