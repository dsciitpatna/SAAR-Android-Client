package com.example.saar.ChangeCredentials;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.saar.R;

public class ChangeCredentialsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_credentials);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (getIntent().getStringExtra("EXTRA").equals("openFragment")) {

            ft.replace(R.id.fragment_credentials_container, new ForgotPasswordFragment());
            ft.commit();
        }else if (getIntent().getStringExtra("EXTRA").equals("openChangeEmail")){
            ft.replace(R.id.fragment_credentials_container, new ChangeEmailFragment());
            ft.commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //handle back button action
        onBackPressed();
        return true;
    }
}