package com.example.saar.Profile;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.saar.Constant;
import com.example.saar.OtpActivity;
import com.example.saar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class EditProfileFragment extends Fragment {

    Spinner spinnerEmploymentType;
    String employment_type;
    SharedPreferences preferences;
    SharedPreferences.Editor sharedPreferenceEditor;
    ImageView profile_image_view;
    TextView  phone_view, fb_link_view, linkedin_link_view;
    TextView present_employer_view, designation_view, address_view, country_view, city_view, state_view, achievements_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        spinnerEmploymentType = (Spinner) rootView.findViewById(R.id.spinner_profile_employment);
        ArrayAdapter<CharSequence> spinnerEmploymentTypeArrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.employment_type_array, android.R.layout.simple_spinner_item);
        spinnerEmploymentTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmploymentType.setAdapter(spinnerEmploymentTypeArrayAdapter);

        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferenceEditor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();

        Button saveButton = (Button) rootView.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("Save changes?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateDatas();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        });

        setupViews(rootView);
        setUpUi();
        return rootView;
    }

    //Function that binds the view with their id
    private void setupViews(View rootView) {
        profile_image_view=rootView.findViewById(R.id.profile_picture);
        phone_view=rootView.findViewById(R.id.edit_phone);
        fb_link_view=rootView.findViewById(R.id.edit_fb_link);
        linkedin_link_view=rootView.findViewById(R.id.edit_linkedin_link);
        present_employer_view=rootView.findViewById(R.id.edit_present_employer);
        designation_view=rootView.findViewById(R.id.edit_designation);
        address_view=rootView.findViewById(R.id.edit_address);
        country_view=rootView.findViewById(R.id.edit_country);
        city_view=rootView.findViewById(R.id.edit_city);
        state_view=rootView.findViewById(R.id.edit_state);
        achievements_view=rootView.findViewById(R.id.edit_achievements);
    }

    //Function that fills the views with the datas
    private void setUpUi() {
        Glide.with(getActivity())
                .load(preferences.getString(Constant.IMG_URL,""))
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(profile_image_view);

        phone_view.setText(preferences.getString(Constant.PHONE,""));
        fb_link_view.setText(preferences.getString(Constant.FB_LINK,""));
        linkedin_link_view.setText(preferences.getString(Constant.LINKEDIN_LINK,""));
        present_employer_view.setText(preferences.getString(Constant.PRESENT_EMPLOYER,""));
        designation_view.setText(preferences.getString(Constant.DESIGNATION,""));
        address_view.setText(preferences.getString(Constant.ADDRESS,""));
        country_view.setText(preferences.getString(Constant.COUNTRY,""));
        city_view.setText(preferences.getString(Constant.CITY,""));
        state_view.setText(preferences.getString(Constant.STATE,""));
        achievements_view.setText(preferences.getString(Constant.ACHIEVEMENTS,""));
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
    }

    //Upload the datas to the server
    private void updateDatas() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.UPDATE_PROFILE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Timber.d(response);
                Log.d("KHANKI","Response - " + response);
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
                        Toast.makeText(getActivity(), getString(R.string.profile_update_failue), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Timber.d(error.toString());
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("rollno", preferences.getString(Constant.ROLLNO,""));
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
}
