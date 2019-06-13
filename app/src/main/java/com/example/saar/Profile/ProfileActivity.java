package com.example.saar.Profile;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.saar.MainActivity;
import com.example.saar.R;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //loading the default fragment
        loadFragment(new ViewProfileFragment());

        //setting back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private boolean loadFragment(Fragment fragment) {

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_profile_container, fragment);
            ft.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        //handle back button action
        onBackPressed();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_profile_container);

        if (id == android.R.id.home) {
            if (fragment instanceof ViewProfileFragment) {
                onBackPressed();
                return true;
            } else if (fragment instanceof EditProfileFragment) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Do you want to exit without saving changes?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment_profile_container, new ViewProfileFragment());
                                ft.commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
