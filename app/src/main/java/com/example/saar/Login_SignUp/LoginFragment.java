package com.example.saar.Login_SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.saar.Constant;
import com.example.saar.ForgotPasswordFragment;
import com.example.saar.MainActivity;
import com.example.saar.OtpActivity;
import com.example.saar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class LoginFragment extends Fragment {

    TextView verifyOTP, forgotPassword;
    EditText emailText, passwordText;
    Button loginButton;
    String email, password;
    SharedPreferences.Editor sharedPreferenceEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        verifyOTP = rootView.findViewById(R.id.verify_otp);
        forgotPassword = rootView.findViewById(R.id.forgot_password);
        emailText = rootView.findViewById(R.id.username_text);
        passwordText = rootView.findViewById(R.id.password_text);
        loginButton = rootView.findViewById(R.id.login_button);
        sharedPreferenceEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.saar_login);

        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OtpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatas();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new ForgotPasswordFragment());
                ft.commit();
            }
        });
    }

    private void getDatas() {
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.credential_empty), Toast.LENGTH_LONG).show();
        } else {
            login();
        }

    }

    private void login() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Timber.d(response);
                Log.d("KHANKI", response);
                // otp_progress.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));
                    if (status == 202) {
                        Timber.d(getString(R.string.loggedin));
                        Toast.makeText(getContext(), getString(R.string.loggedin), Toast.LENGTH_LONG).show();

                        //Call the function to parse the JSON data
                        storeUserInfo(jsonObject);

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        Timber.d(getString(R.string.error_loggingin));
                        JSONArray jsonArray = jsonObject.getJSONArray("messages");
                        Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_LONG).show();
                        Timber.d(jsonArray.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //otp_progress.setVisibility(View.GONE);
                Timber.d(error.toString());
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Request<String> data = requestQueue.add(stringRequest);
    }

    //A function that parses the success response, and stores the user data in the app.
    private void storeUserInfo(JSONObject jsonObject) throws JSONException {
        JSONObject mJsonObject = jsonObject.getJSONObject("messages");
        sharedPreferenceEditor.putBoolean(Constant.LOGIN_STATUS, true);
        sharedPreferenceEditor.putString(Constant.ROLLNO, mJsonObject.get(Constant.ROLLNO).toString());
        sharedPreferenceEditor.putString(Constant.FIRST_NAME, mJsonObject.getString(Constant.FIRST_NAME));
        sharedPreferenceEditor.putString(Constant.LAST_NAME, mJsonObject.getString(Constant.LAST_NAME));
        sharedPreferenceEditor.putString(Constant.EMAIL, mJsonObject.getString(Constant.EMAIL));
        sharedPreferenceEditor.putString(Constant.PHONE, mJsonObject.getString(Constant.PHONE));
        sharedPreferenceEditor.putString(Constant.FB_LINK, mJsonObject.getString(Constant.FB_LINK));
        sharedPreferenceEditor.putString(Constant.LINKEDIN_LINK, mJsonObject.getString(Constant.LINKEDIN_LINK));
        sharedPreferenceEditor.putString(Constant.DOB, mJsonObject.getString(Constant.DOB));
        sharedPreferenceEditor.putString(Constant.GRADUATION_YEAR, mJsonObject.getString(Constant.GRADUATION_YEAR));
        sharedPreferenceEditor.putString(Constant.DEGREE, mJsonObject.getString(Constant.DEGREE));
        sharedPreferenceEditor.putString(Constant.DEPARTMENT, mJsonObject.getString(Constant.DEPARTMENT));
        sharedPreferenceEditor.putString(Constant.EMPLOYEMENT_TYPE, mJsonObject.getString(Constant.EMPLOYEMENT_TYPE));
        sharedPreferenceEditor.putString(Constant.PRESENT_EMPLOYER, mJsonObject.getString(Constant.PRESENT_EMPLOYER));
        sharedPreferenceEditor.putString(Constant.DESIGNATION, mJsonObject.getString(Constant.DESIGNATION));
        sharedPreferenceEditor.putString(Constant.ADDRESS, mJsonObject.getString(Constant.ADDRESS));
        sharedPreferenceEditor.putString(Constant.COUNTRY, mJsonObject.getString(Constant.COUNTRY));
        sharedPreferenceEditor.putString(Constant.CITY, mJsonObject.getString(Constant.CITY));
        sharedPreferenceEditor.putString(Constant.STATE, mJsonObject.getString(Constant.STATE));
        sharedPreferenceEditor.putString(Constant.ACHIEVEMENTS, mJsonObject.getString(Constant.ACHIEVEMENTS));
        sharedPreferenceEditor.apply();
    }
}
