package com.example.saar.Share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.saar.Constant;
import com.example.saar.R;

public class ShareFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_share, container, false);

        ImageButton facebookButton = (ImageButton) rootView.findViewById(R.id.share_facebook);
        facebookButton.setOnClickListener(this);

        ImageButton instagramButton = (ImageButton) rootView.findViewById(R.id.share_instagram);
        instagramButton.setOnClickListener(this);

        ImageButton twitterButton = (ImageButton) rootView.findViewById(R.id.share_twitter);
        twitterButton.setOnClickListener(this);

        ImageButton linkedinButton = (ImageButton) rootView.findViewById(R.id.share_linkedin);
        linkedinButton.setOnClickListener(this);

        ImageButton youtubeButton = (ImageButton) rootView.findViewById(R.id.share_youtube);
        youtubeButton.setOnClickListener(this);

        ImageButton websiteButton = (ImageButton) rootView.findViewById(R.id.share_website);
        websiteButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.share_fragment);
    }

    @Override
    public void onClick(View v) {
        Intent browserIntent;
        switch (v.getId()) {
            case R.id.share_facebook:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.FACEBOOK_LINK));
                startActivity(browserIntent);
                break;
            case R.id.share_instagram:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.INSTAGRAM_LINK));
                startActivity(browserIntent);
                break;
            case R.id.share_twitter:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.TWITTER_LINK));
                startActivity(browserIntent);
                break;
            case R.id.share_linkedin:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.LINKEDIN_LINK));
                startActivity(browserIntent);
                break;
            case R.id.share_youtube:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.YOUTUBE_LINK));
                startActivity(browserIntent);
                break;
            case R.id.share_website:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.WEBSITE_LINK));
                startActivity(browserIntent);
                break;
        }
    }
}
