package com.example.saar.Login_SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.saar.Constant;
import com.example.saar.MainActivity;
import com.example.saar.R;

public class LoginSignupActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    SharedPreferences preferences;
    SharedPreferences.Editor sharedPreferenceEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferenceEditor = preferences.edit();
        if (preferences.getBoolean(Constant.LOGIN_STATUS, false)) {
            //user logged in
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            //not logged in
            setContentView(R.layout.activity_login_signup);

            //loading the default fragment
            loadFragment(new LoginFragment());

            //getting bottom navigation view and attaching the listener
            BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
            navigation.setOnNavigationItemSelectedListener(this);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.login:
                fragment = new LoginFragment();
                break;

            case R.id.signup:
                fragment = new SignUpFragment();
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharedPreferenceEditor.putBoolean(Constant.SKIP_LOGIN, true);
        sharedPreferenceEditor.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
