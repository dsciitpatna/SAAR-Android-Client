package com.example.saar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class ShareFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_share, container, false);

        ImageButton facebookButton = (ImageButton) v.findViewById(R.id.share_facebook);
        facebookButton.setOnClickListener(this);

        ImageButton instagramButton = (ImageButton) v.findViewById(R.id.share_instagram);
        instagramButton.setOnClickListener(this);

        ImageButton twitterButton = (ImageButton) v.findViewById(R.id.share_twitter);
        twitterButton.setOnClickListener(this);

        ImageButton linkedinButton = (ImageButton) v.findViewById(R.id.share_linkedin);
        linkedinButton.setOnClickListener(this);

        ImageButton youtubeButton = (ImageButton) v.findViewById(R.id.share_youtube);
        youtubeButton.setOnClickListener(this);

        ImageButton websiteButton = (ImageButton) v.findViewById(R.id.share_website);
        websiteButton.setOnClickListener(this);

        return v;
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
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/saar.iitp/"));
                startActivity(browserIntent);
                break;
            case R.id.share_instagram:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/saar.iitp/"));
                startActivity(browserIntent);
                break;
            case R.id.share_twitter:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/saar_iitp"));
                startActivity(browserIntent);
                break;
            case R.id.share_linkedin:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/saar-iitp"));
                startActivity(browserIntent);
                break;
            case R.id.share_youtube:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCoAb1YggffbfgVxQyXaZB-g"));
                startActivity(browserIntent);
                break;
            case R.id.share_website:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://saar.iitp.ac.in/"));
                startActivity(browserIntent);
                break;
        }
    }
}
