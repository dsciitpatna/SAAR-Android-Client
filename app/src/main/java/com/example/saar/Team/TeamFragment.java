package com.example.saar.Team;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saar.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeamFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team, container, false);

        int IMAGE_ID[] = {R.drawable.vivek_garg, R.drawable.mohit_kishore, R.drawable.hit_vardhan, R.drawable.utkarsh, R.drawable.raghu, R.drawable.anuj_shastri, R.drawable.saurabh_gupta,
                R.drawable.avinash_singh, R.drawable.himanshu_gupta, R.drawable.vatsal_singhal, R.drawable.adarsh, R.drawable.lavanya_naresh, R.drawable.gaurav_sharma, R.drawable.rakesh_kumar,
                R.drawable.diptanil_sarkar, R.drawable.aman_kumar, R.drawable.sahebjeet, R.drawable.aarohan_panda, R.drawable.akash_balaji, R.drawable.anuj_yadav, R.drawable.nischal,
                R.drawable.atul_gupta, R.drawable.rahul_verma, R.drawable.gaurav_pratap_singh, R.drawable.aparsh_gupta, R.drawable.somenath_sarkar, R.drawable.pranay, R.drawable.amartya_mondal,
                R.drawable.abhinav_gyan};

        int IMAGE_VIEW_ID[] = {R.id.team_member_1, R.id.team_member_2, R.id.team_member_3, R.id.team_member_4, R.id.team_member_5, R.id.team_member_6, R.id.team_member_7, R.id.team_member_8, R.id.team_member_9,
                R.id.team_member_10, R.id.team_member_11, R.id.team_member_12, R.id.team_member_13, R.id.team_member_14, R.id.team_member_15, R.id.team_member_16, R.id.team_member_17, R.id.team_member_18,
                R.id.team_member_19, R.id.team_member_20, R.id.team_member_21, R.id.team_member_22, R.id.team_member_23, R.id.team_member_24, R.id.team_member_25, R.id.team_member_26, R.id.team_member_27,
                R.id.team_member_28, R.id.team_member_29};

        for (int position = 0; position < IMAGE_ID.length; position++) {
            CircleImageView circleImageView = rootView.findViewById(IMAGE_VIEW_ID[position]);
            //circleImageView.setImageResource(IMAGE_ID[position]);
            circleImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), IMAGE_ID[position], 70, 70));
        }


        rootView.findViewById(IMAGE_VIEW_ID[0]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100006939611682");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[1]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100002071893465");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[2]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100001292305988");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[3]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100005407387606");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[4]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/veerapaneni.raghuvamsi");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[5]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100020094970927");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[6]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100020806422079");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[7]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100021493361913");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[8]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100005947187499");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[9]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100001049708197");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[10]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100005422239142");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[11]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100010552711082");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[12]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100009881434986");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[13]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100005625486492");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[14]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100026540102843");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[15]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100007750481987");
            }

        });
        rootView.findViewById(IMAGE_VIEW_ID[16]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100005452056853");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[17]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/arohan.panda.3");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[18]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/akash.balaji.900");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[19]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100014878428318");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[20]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100006820958725");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[21]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100011082333729");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[22]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100009451491493");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[23]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/profile.php?id=100004723033463");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[24]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100024420822716");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[25]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100003569846355");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[26]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100027472536809");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[27]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/100008864768272");
            }
        });

        rootView.findViewById(IMAGE_VIEW_ID[28]).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_to_fb("https://www.facebook.com/theabhinavgyan");
            }
        });

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

    private void go_to_fb(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(id));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), getString(R.string.team_fb_intent_error), Toast.LENGTH_SHORT).show();
        }
    }
}
