package com.example.saar;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class TeamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);

        int IMAGE_ID[] = {R.drawable.vivek_garg, R.drawable.mohit_kishore, R.drawable.hit_vardhan, R.drawable.utkarsh, R.drawable.saar_logo, R.drawable.anuj_shastri, R.drawable.saurabh_gupta,
                R.drawable.avinash_singh, R.drawable.himanshu_gupta, R.drawable.vatsal_singhal, R.drawable.adarsh, R.drawable.lavanya_naresh, R.drawable.gaurav_sharma, R.drawable.rakesh_kumar,
                R.drawable.diptanil_sarkar, R.drawable.aman_kumar, R.drawable.sahebjeet, R.drawable.aarohan_panda, R.drawable.akash_balaji, R.drawable.anuj_yadav, R.drawable.nischal,
                R.drawable.atul_gupta, R.drawable.rahul_verma, R.drawable.gaurav_pratap_singh, R.drawable.aparsh_gupta, R.drawable.somenath_sarkar, R.drawable.pranay, R.drawable.amartya_mondal,
                R.drawable.abhinav_gyan};

        int IMAGE_VIEW_ID[] = {R.id.team_member_1, R.id.team_member_2, R.id.team_member_3, R.id.team_member_4, R.id.team_member_5, R.id.team_member_6, R.id.team_member_7, R.id.team_member_8, R.id.team_member_9,
                R.id.team_member_10, R.id.team_member_11, R.id.team_member_12, R.id.team_member_13, R.id.team_member_14, R.id.team_member_15, R.id.team_member_16, R.id.team_member_17, R.id.team_member_18,
                R.id.team_member_19, R.id.team_member_20, R.id.team_member_21, R.id.team_member_22, R.id.team_member_23, R.id.team_member_24, R.id.team_member_25, R.id.team_member_26, R.id.team_member_27,
                R.id.team_member_28, R.id.team_member_29};

        for (int position = 0; position < IMAGE_ID.length; position++) {
            ((ImageView) rootView.findViewById(IMAGE_VIEW_ID[position])).setImageBitmap(decodeSampledBitmapFromResource(getResources(), IMAGE_ID[position], 70, 70));
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.team_fragment);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
