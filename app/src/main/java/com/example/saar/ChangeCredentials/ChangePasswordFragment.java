package com.example.saar.ChangeCredentials;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.saar.Login_SignUp.LoginSignupActivity;
import com.example.saar.R;
import com.example.saar.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class ChangePasswordFragment extends Fragment {

    EditText old_password, new_password, confirm_new_password;
    Button reset_password;
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean forgot_password = false;
    String rollno;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        old_password = rootView.findViewById(R.id.chng_old_password);
        new_password = rootView.findViewById(R.id.chng_new_password);
        confirm_new_password = rootView.findViewById(R.id.chng_confirm_new_password);
        reset_password = rootView.findViewById(R.id.password_reset_button);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.change_password_fragment);

        if(getActivity().getIntent().hasExtra("rollno")){
            forgot_password=true;
            old_password.setText("Dummy");
            view.findViewById(R.id.oldPasswordInputLayout).setVisibility(View.GONE);
            rollno=getActivity().getIntent().getStringExtra("rollno");
        }

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password_text = old_password.getText().toString();
                String new_password_text = new_password.getText().toString();
                String confirm_new_password_text = confirm_new_password.getText().toString();

                if (old_password_text.isEmpty() || new_password_text.isEmpty() || confirm_new_password_text.isEmpty())
                    Toast.makeText(getContext(), getResources().getString(R.string.enter_all_fields), Toast.LENGTH_LONG).show();
                else if (!(new_password_text.equals(confirm_new_password_text)))
                    Toast.makeText(getContext(), getResources().getString(R.string.password_mismatch), Toast.LENGTH_LONG).show();
                else if (Utils.isNetworkConnected(getContext())) {
                    resetPassword();
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void resetPassword() {
        Utils.closeKeyboard(getView(), getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.change_password_reset));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.CHANGE_PASSWORD_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Timber.d(response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));
                    if (status == 207) {
                        Toast.makeText(getContext(), getString(R.string.change_password_succesfull), Toast.LENGTH_LONG).show();
                        if(preferences.getBoolean(Constant.LOGIN_STATUS,false)){
                            Utils.unsuscribeFromNotification(preferences.getString(Constant.ROLLNO, ""));
                            Utils.logout(editor, getContext());
                        }else{
                            Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                            startActivity(intent);
                        }

                    } else {

                        JSONArray jsonArray = jsonObject.getJSONArray("messages");
                        Toast.makeText(getActivity(), jsonArray.toString(), Toast.LENGTH_LONG).show();
                        Timber.d(jsonArray.toString());
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

                params.put("old_password", old_password.getText().toString());
                params.put("new_password", new_password.getText().toString());
                params.put("confirm_password", confirm_new_password.getText().toString());
                if(forgot_password){
                    params.put("forgot_password","forgot_password");
                    params.put("rollno",rollno);
                }else{
                    params.put("rollno", preferences.getString(Constant.ROLLNO, ""));
                }
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
