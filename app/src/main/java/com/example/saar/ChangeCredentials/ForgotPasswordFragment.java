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

public class ForgotPasswordFragment extends Fragment {

    EditText email, rollno;
    Button forgotPassword;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        email = rootView.findViewById(R.id.email_text_forgot_password);
        rollno = rootView.findViewById(R.id.roll_text_forgot_password);
        forgotPassword = rootView.findViewById(R.id.forgot_password_button);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.forgot_password);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = email.getText().toString();
                String rollno_text = rollno.getText().toString();
                if (email_text.isEmpty() || rollno_text.isEmpty())
                    Toast.makeText(getContext(), getResources().getString(R.string.enter_all_fields), Toast.LENGTH_LONG).show();
                else if (Utils.isNetworkConnected(getContext()))
                    requestDatas();
                else
                    Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestDatas() {
        Utils.closeKeyboard(getView(), getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.forgot_password_request));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.FORGOT_PASSWORD_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Timber.d(response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));

                    if (status == 204) {
                        Timber.d(getString(R.string.success_forgot_password_request));
                        Toast.makeText(getContext(), getString(R.string.success_forgot_password_request), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getContext(), OtpActivity.class);
                        intent.putExtra("rollno", rollno.getText().toString());
                        intent.putExtra("forgot_password", "forgot_password");
                        startActivity(intent);
                    } else {
                        Timber.d(getString(R.string.error_Forgot_password));
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
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Timber.d(error.toString());
            }

        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email.getText().toString());
                params.put("rollno", rollno.getText().toString());
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
