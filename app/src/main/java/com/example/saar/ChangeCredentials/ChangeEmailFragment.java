package com.example.saar.ChangeCredentials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.saar.OtpActivity;
import com.example.saar.R;
import com.example.saar.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class ChangeEmailFragment extends Fragment {

    EditText old_email, new_email, password, rollno;
    Button emailChangeButton;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_change_email, container, false);
        old_email = rootView.findViewById(R.id.old_email_text);
        new_email = rootView.findViewById(R.id.new_email_text);
        password = rootView.findViewById(R.id.password_text);
        rollno = rootView.findViewById(R.id.roll_text);
        emailChangeButton = rootView.findViewById(R.id.email_change_button);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.change_email);

        emailChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_email_text = old_email.getText().toString();
                String new_email_text = new_email.getText().toString();
                String password_text = password.getText().toString();
                String roll_text = rollno.getText().toString();

                if (old_email_text.isEmpty() || new_email_text.isEmpty() || password_text.isEmpty() || roll_text.isEmpty())
                    Toast.makeText(getContext(), getResources().getString(R.string.enter_all_fields), Toast.LENGTH_LONG).show();
                else if (Utils.isNetworkConnected(getContext())) {
                    requestEmailChange();
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void requestEmailChange() {
        Utils.closeKeyboard(getView(), getContext());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.change_email_request));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHANGE_EMAIL_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Timber.d(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));
                    if (status == 203) {
                        Timber.d(getString(R.string.change_email_success));
                        Toast.makeText(getContext(), getString(R.string.change_email_success), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(), OtpActivity.class);
                        intent.putExtra("rollno", rollno.getText().toString());
                        startActivity(intent);

                    } else {

                        JSONArray jsonArray = jsonObject.getJSONArray("messages");
                        Timber.d(getString(R.string.change_email_failure));
                        Toast.makeText(getContext(), getString(R.string.change_email_failure), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                signupProgress.setVisibility(View.GONE);
                progressDialog.dismiss();
                Timber.d(error.toString());
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("new_email", new_email.getText().toString());
                params.put("old_email", old_email.getText().toString());
                params.put("password", password.getText().toString());

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
}
