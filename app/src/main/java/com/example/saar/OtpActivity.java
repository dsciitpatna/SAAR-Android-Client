package com.example.saar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import static java.lang.Integer.valueOf;

public class OtpActivity extends AppCompatActivity {

    private Button resend;
    private TextView timer;
    private int counter;
    Pinview pinview;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.otp_next);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            //if otp is correct

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OtpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        pinview = (Pinview) findViewById(R.id.mypinview);

        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Toast.makeText(getApplicationContext(), pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        });

        timer = (TextView) findViewById(R.id.timer);
        resend = (Button) findViewById(R.id.resend_button);

        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                int time = valueOf(counter);
                time = 30 - time;
                if (time >= 10)
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
        }, 30000);

    }

    @Override
    public boolean onSupportNavigateUp() {
        //handle back button action
        onBackPressed();
        return true;
    }
}