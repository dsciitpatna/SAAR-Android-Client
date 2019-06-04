package com.example.saar.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saar.About.AboutUsFragment;
import com.example.saar.Contact.ContactFragment;
import com.example.saar.Donate.DonateFragment;
import com.example.saar.Gallery.GalleryFragment;
import com.example.saar.R;
import com.example.saar.Team.TeamFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.app_name);
        Button about, gallery, team;
        TextView contact;
        LinearLayout donate;
        about = view.findViewById(R.id.go_to_about);
        gallery = view.findViewById(R.id.go_to_gallery);
        team = view.findViewById(R.id.go_to_team);
        contact = view.findViewById(R.id.go_to_contact);
        donate = view.findViewById(R.id.go_to_donate);
        about.setOnClickListener(this);
        gallery.setOnClickListener(this);
        team.setOnClickListener(this);
        contact.setOnClickListener(this);
        donate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.go_to_about:
                fragment = new AboutUsFragment();
                break;
            case R.id.go_to_gallery:
                fragment = new GalleryFragment();
                break;
            case R.id.go_to_team:
                fragment = new TeamFragment();
                break;
            case R.id.go_to_donate:
                fragment = new DonateFragment();
                break;
            case R.id.go_to_contact:
                fragment = new ContactFragment();
                break;
        }
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }
}
