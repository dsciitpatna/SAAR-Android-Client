package com.example.saar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
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
import com.example.saar.ChangeCredentials.ChangeCredentialsActivity;
import com.example.saar.Login_SignUp.LoginSignupActivity;
import com.goodiebag.pinview.Pinview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

import static java.lang.Integer.valueOf;

public class OtpActivity extends AppCompatActivity {

    private Button resend;
    private TextView timer;
    EditText otpRollNo;
    private int counter;
    Pinview pinview;
    FloatingActionButton sendOTP;
    String otpValue = "", rollno;
    ProgressDialog progressDialog;
    Boolean forgot_password = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pinview = (Pinview) findViewById(R.id.otpView);

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                otpValue = pinview.getValue();
            }
        });

        otpRollNo = (EditText) findViewById(R.id.otp_rollno);

        timer = (TextView) findViewById(R.id.timer);
        resend = (Button) findViewById(R.id.resend_button);

        //Test case if user wants to verify after some time
        if (getIntent().hasExtra("rollno")) {
            rollno = getIntent().getStringExtra("rollno");
            getIntent().removeExtra("rollno");
            otpRollNo.setVisibility(View.INVISIBLE);
            otpRollNo.setText(rollno);
            setUpTimer();
        } else {
            otpRollNo.setVisibility(View.VISIBLE);
            resend.setVisibility(View.VISIBLE);
        }

        if (getIntent().hasExtra("forgot_password")) {
            forgot_password = true;
        } else {
            forgot_password = false;
        }
        //Action to be performed when the sending otp button is pressed
        sendOTP = (FloatingActionButton) findViewById(R.id.otp_next);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otpValue.length() != 0) {
                    rollno = otpRollNo.getText().toString();
                    verifyOTP();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.error_entering_otp), Toast.LENGTH_SHORT).show();
                    Timber.d(getString(R.string.error_entering_otp));
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTP();
            }
        });

    }

    private void resendOTP() {
        rollno = otpRollNo.getText().toString();

        if (rollno.isEmpty()) {
            Toast.makeText(this, getString(R.string.rollno_otp), Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < pinview.getPinLength(); i++) {
                pinview.onKey(pinview.getFocusedChild(), KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
            }
            final ProgressDialog resendProgressDialog = new ProgressDialog(this);
            resendProgressDialog.setMessage(getString(R.string.resend_otp_request));
            resendProgressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.RESEND_OTP_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Timber.d(response);
                    resendProgressDialog.dismiss();
                    resend.setVisibility(View.GONE);
                    setUpTimer();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int status = Integer.parseInt(jsonObject.getString("status"));
                        if (status == 201) {
                            Timber.d(getString(R.string.otp_verified));
                            Toast.makeText(OtpActivity.this, getString(R.string.resend_otp_succesfull), Toast.LENGTH_LONG).show();
                            otpRollNo.setVisibility(View.GONE);

                        } else {

                            JSONArray jsonArray = jsonObject.getJSONArray("messages");
                            Toast.makeText(OtpActivity.this, jsonArray.toString(), Toast.LENGTH_LONG).show();
                            Timber.d(jsonArray.toString());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    resendProgressDialog.dismiss();
                    Timber.d(error.toString());
                }

            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rollno", rollno);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
            Request<String> data = requestQueue.add(stringRequest);
        }
    }

    private void setUpTimer() {

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                int time = valueOf(counter);
                time = 60 - time;
                if (time == 60)
                    timer.setText("1:00");
                else if (time >= 10)
                    timer.setText("0:" + time);
                else
                    timer.setText("0:0" + time);
                counter++;
            }

            public void onFinish() {
                timer.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);
            }
        }.start();

        resend.postDelayed(new Runnable() {
            public void run() {
                resend.setVisibility(View.VISIBLE);
            }
        }, 60000);
    }

    private void verifyOTP() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.verify_otp));
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.OTP_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Timber.d(response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));
                    if (status == 201) {
                        Timber.d(getString(R.string.otp_verified));
                        Toast.makeText(OtpActivity.this, getString(R.string.otp_verified), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(OtpActivity.this, LoginSignupActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else if (status == 208) {
                        Timber.d(getString(R.string.forgot_password_success));
                        Intent intent = new Intent(OtpActivity.this, ChangeCredentialsActivity.class);
                        intent.putExtra("EXTRA", "openChangePassword");
                        intent.putExtra("rollno", rollno);
                        startActivity(intent);

                    } else {

                        JSONArray jsonArray = jsonObject.getJSONArray("messages");
                        Toast.makeText(OtpActivity.this, jsonArray.toString(), Toast.LENGTH_LONG).show();
                        Timber.d(jsonArray.toString());
                        for (int i = 0; i < pinview.getPinLength(); i++) {
                            pinview.onKey(pinview.getFocusedChild(), KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
                        }
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
                params.put("rollno", rollno);
                params.put("verification_code", otpValue);
                if (forgot_password) {
                    params.put("forgot_password", "forgot_password");
                }
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(OtpActivity.this);
        Request<String> data = requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        //handle back button action
        onBackPressed();
        return true;
    }
}