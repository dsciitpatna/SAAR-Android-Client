package com.example.saar.Profile;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.saar.Constant;
import com.example.saar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class EditProfileFragment extends Fragment {

    Spinner spinnerEmploymentType;
    String employment_type;
    Integer employment_type_position;
    FloatingActionButton change_photo_button;
    SharedPreferences preferences;
    SharedPreferences.Editor sharedPreferenceEditor;
    CircleImageView profile_image_view;
    TextView phone_view, fb_link_view, linkedin_link_view;
    TextView present_employer_view, designation_view, address_view, country_view, city_view, state_view, achievements_view;
    private static Integer GALLERY = 1;
    private static Integer CAMERA = 2;
    private static Integer RECORD_REQUEST_CODE = 101;
    Bitmap myBitmap;
    ProgressDialog progressDialog;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        spinnerEmploymentType = (Spinner) rootView.findViewById(R.id.spinner_profile_employment);
        ArrayAdapter<CharSequence> spinnerEmploymentTypeArrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.employment_type_array, android.R.layout.simple_spinner_item);
        spinnerEmploymentTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmploymentType.setAdapter(spinnerEmploymentTypeArrayAdapter);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferenceEditor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();

        employment_type_position = spinnerEmploymentTypeArrayAdapter.getPosition(preferences.getString(Constant.EMPLOYEMENT_TYPE, ""));

        setupViews(rootView);
        setUpUi();

        Button saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage(getString(R.string.alert_profile_message)).setCancelable(true)
                        .setPositiveButton(getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateDatas();
                            }
                        })
                        .setNegativeButton(getString(R.string.alert_negative), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        change_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImages();
            }
        });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();

        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setMessage(getString(R.string.exit_profile_alert)).setCancelable(false)
                                .setPositiveButton(getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.fragment_profile_container, new ViewProfileFragment());
                                        ft.commit();
                                    }
                                })
                                .setNegativeButton(getString(R.string.alert_negative), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).show();
                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    //Function that binds the view with their id
    private void setupViews(View rootView) {
        profile_image_view = rootView.findViewById(R.id.profile_picture);
        phone_view = rootView.findViewById(R.id.edit_phone);
        fb_link_view = rootView.findViewById(R.id.edit_fb_link);
        linkedin_link_view = rootView.findViewById(R.id.edit_linkedin_link);
        present_employer_view = rootView.findViewById(R.id.edit_present_employer);
        designation_view = rootView.findViewById(R.id.edit_designation);
        address_view = rootView.findViewById(R.id.edit_address);
        country_view = rootView.findViewById(R.id.edit_country);
        city_view = rootView.findViewById(R.id.edit_city);
        state_view = rootView.findViewById(R.id.edit_state);
        achievements_view = rootView.findViewById(R.id.edit_achievements);
        change_photo_button = rootView.findViewById(R.id.profile_fab);
        progressBar = rootView.findViewById(R.id.edit_profile_photo_progress);
    }

    //Function that fills the views with the datas
    private void setUpUi() {
        Glide.with(getActivity())
                .load(preferences.getString(Constant.IMG_URL, ""))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.ic_account_circle_black_48dp)
                .into(profile_image_view);

        phone_view.setText(preferences.getString(Constant.PHONE, ""));
        fb_link_view.setText(preferences.getString(Constant.FB_LINK, ""));
        linkedin_link_view.setText(preferences.getString(Constant.LINKEDIN_LINK, ""));
        present_employer_view.setText(preferences.getString(Constant.PRESENT_EMPLOYER, ""));
        designation_view.setText(preferences.getString(Constant.DESIGNATION, ""));
        address_view.setText(preferences.getString(Constant.ADDRESS, ""));
        country_view.setText(preferences.getString(Constant.COUNTRY, ""));
        city_view.setText(preferences.getString(Constant.CITY, ""));
        state_view.setText(preferences.getString(Constant.STATE, ""));
        achievements_view.setText(preferences.getString(Constant.ACHIEVEMENTS, ""));
        spinnerEmploymentType.setSelection(employment_type_position);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.edit_profile_fragment);

        spinnerEmploymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employment_type = spinnerEmploymentType.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        makeRequest();
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RECORD_REQUEST_CODE);
    }

    //Upload the datas to the server
    private void updateDatas() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.edit_profile_progress));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPDATE_PROFILE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Timber.d(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));
                    if (status == 205) {
                        Timber.d(getString(R.string.profile_update_success));
                        Toast.makeText(getActivity(), getString(R.string.profile_update_success), Toast.LENGTH_LONG).show();

                        updatePreferences();//Updates the values in the shared preferences

                        Intent intent = new Intent(getActivity(), ProfileActivity.class);
                        startActivity(intent);

                    } else {

                        JSONArray jsonArray = jsonObject.getJSONArray("messages");
                        Timber.d(getString(R.string.profile_update_failue));
                        Timber.d(jsonArray.toString());
                        Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Timber.d(error.toString());
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rollno", preferences.getString(Constant.ROLLNO, ""));
                params.put("phone", phone_view.getText().toString());
                params.put("fb_link", fb_link_view.getText().toString());
                params.put("linkedin_link", linkedin_link_view.getText().toString());
                params.put("employment_type", employment_type);
                params.put("present_employer", present_employer_view.getText().toString());
                params.put("designation", designation_view.getText().toString());
                params.put("address", address_view.getText().toString());
                params.put("country", country_view.getText().toString());
                params.put("state", state_view.getText().toString());
                params.put("city", city_view.getText().toString());
                params.put("achievements", achievements_view.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Request<String> data = requestQueue.add(stringRequest);
    }

    private void updatePreferences() {
        sharedPreferenceEditor.putString(Constant.PHONE, phone_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.FB_LINK, fb_link_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.LINKEDIN_LINK, linkedin_link_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.EMPLOYEMENT_TYPE, employment_type);
        sharedPreferenceEditor.putString(Constant.PRESENT_EMPLOYER, present_employer_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.DESIGNATION, designation_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.ACHIEVEMENTS, achievements_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.ADDRESS, address_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.COUNTRY, country_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.CITY, city_view.getText().toString());
        sharedPreferenceEditor.putString(Constant.STATE, state_view.getText().toString());
        sharedPreferenceEditor.apply();

    }

    private void captureImages() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle(getString(R.string.profile_img_alert_title));
        pictureDialog.setMessage(getString(R.string.profile_img_alert_message));
        pictureDialog.setPositiveButton(
                getString(R.string.gallery),
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        choosePhotoFromGallery();
                    }
                }
        );

        pictureDialog.setNegativeButton(
                getString(R.string.camera),
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        // If user click no
                        // then dialog box is canceled.
                        getPhotoFromCamera();
                    }
                });

        AlertDialog alertDialog = pictureDialog.create();
        alertDialog.show();

    }

    private void getPhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            makeRequest();
        else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
    }

    private void choosePhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED)
            makeRequest();
        else {
            Intent galIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galIntent, GALLERY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentUri);
                    myBitmap = bitmap;
                    uploadFile();
                    //display.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!!", Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //display.setImageBitmap(thumbnail);
            myBitmap = thumbnail;
            uploadFile();
        }

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadFile() {
        progressBar.setVisibility(View.VISIBLE);
        change_photo_button.hide();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPDATE_PROFILE_IMAGE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                change_photo_button.show();
                Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));

                    if (status == 206) {
                        Toast.makeText(getActivity(), getString(R.string.img_upload_success), Toast.LENGTH_LONG).show();
                        JSONObject mJsonObject = jsonObject.getJSONObject("messages");
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
                        editor.putString(Constant.IMG_URL, mJsonObject.getString(Constant.IMG_URL));
                        editor.apply();
                        Glide.with(getActivity())
                                .load(preferences.getString(Constant.IMG_URL, ""))
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .placeholder(R.drawable.ic_account_circle_black_48dp)
                                .into(profile_image_view);

                    } else {
                        Toast.makeText(getActivity(), getString(R.string.img_upload_failure), Toast.LENGTH_LONG).show();
                        Timber.d(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                change_photo_button.show();
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("image", getStringImage(myBitmap));
                params.put("rollno", preferences.getString(Constant.ROLLNO, ""));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Request<String> data = requestQueue.add(stringRequest);


    }
}
