package com.example.saar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.saar.About.AboutUsFragment;
import com.example.saar.ChangeCredentials.ChangeCredentialsActivity;
import com.example.saar.Contact.ContactFragment;
import com.example.saar.Donate.DonateFragment;
import com.example.saar.Gallery.GalleryFragment;
import com.example.saar.Home.HomeFragment;
import com.example.saar.Login_SignUp.LoginSignupActivity;
import com.example.saar.Profile.ProfileActivity;
import com.example.saar.Share.ShareFragment;
import com.example.saar.Team.TeamFragment;
import com.example.saar.Timeline_Events.TimelineFragment;
import com.example.saar.Utils.Utils;
import com.example.saar.Video.VideosFragment;
import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //creating fragment object
    Fragment fragment = null;
    SharedPreferences preferences;
    SharedPreferences.Editor editor, notifications;
    TextView name, email;
    CircleImageView circleImageView;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (!preferences.getBoolean(Constant.LOGIN_STATUS, false) && !preferences.getBoolean(Constant.SKIP_LOGIN, false)) {
            startActivity(new Intent(this, LoginSignupActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_item = navigationView.getMenu();
        if (preferences.getBoolean(Constant.LOGIN_STATUS, false))
            nav_item.findItem(R.id.nav_profile).setVisible(true);
        else
            nav_item.findItem(R.id.nav_profile).setVisible(false);
        navigationView.setNavigationItemSelectedListener(this);
        showHomeFragment();

        View headerview = navigationView.getHeaderView(0);

        //Initially HomeFragment will be displayed
        displaySelectedScreen(R.id.nav_home);
        subscribeForNotification();

        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.nav_layout);
        name = headerview.findViewById(R.id.nav_header_name);
        email = headerview.findViewById(R.id.nav_header_email);
        header.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        circleImageView = headerview.findViewById(R.id.nav_header_image);
        setHeaderData();
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (preferences.getBoolean(Constant.LOGIN_STATUS, false)) {
                    //user is logged in
                    Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                    MainActivity.this.startActivity(intentProfile);
                } else {
                    //user not logged in
                    Intent intentLogin = new Intent(MainActivity.this, LoginSignupActivity.class);
                    MainActivity.this.startActivity(intentLogin);
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void subscribeForNotification() {
        notifications = PreferenceManager.getDefaultSharedPreferences(this).edit();
        if (!preferences.getBoolean(Constant.SUBSCRIBE_NOTIFICATION, false)) {
            FirebaseMessaging.getInstance().subscribeToTopic("alumnus");
            if (preferences.getBoolean(Constant.LOGIN_STATUS, false)) {
                String rollno = preferences.getString(Constant.ROLLNO, "");

                String batch = Utils.getBatch(rollno);
                String department = Utils.getDepartment(rollno);
                FirebaseMessaging.getInstance().subscribeToTopic(batch);
                FirebaseMessaging.getInstance().subscribeToTopic(department);
            }

            notifications.putBoolean(Constant.SUBSCRIBE_NOTIFICATION, true);
            notifications.apply();
            Toast.makeText(MainActivity.this, getString(R.string.msg_subscribed), Toast.LENGTH_SHORT).show();
            Timber.d("Subscribed to notification.");
        }
    }

    private void showHomeFragment() {
        fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (currentFragment instanceof HomeFragment) {
            super.onBackPressed();
        } else {
            showHomeFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem login = menu.findItem(R.id.action_login_signup);
        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem change_email = menu.findItem(R.id.action_change_email);
        MenuItem change_password = menu.findItem(R.id.action_change_password);
        if (preferences.getBoolean(Constant.LOGIN_STATUS, false)) {
            //user is logged in
            login.setVisible(false);
            logout.setVisible(true);
            change_email.setVisible(true);
            change_password.setVisible(true);
        } else {
            //user is not logged in
            login.setVisible(true);
            logout.setVisible(false);
            change_email.setVisible(false);
            change_password.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login_signup) {
            startActivity(new Intent(this, LoginSignupActivity.class));
        } else if (id == R.id.action_logout) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("Are you sure you want to logout?").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editor = preferences.edit();
                            Utils.unsuscribeFromNotification(preferences.getString(Constant.ROLLNO, ""));
                            Utils.logout(editor, MainActivity.this);
                            finish();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();

        } else if (id == R.id.action_change_email) {
            Intent intent = new Intent(this, ChangeCredentialsActivity.class);
            intent.putExtra("EXTRA", "openChangeEmail");
            startActivity(intent);
        } else if (id == R.id.action_change_password) {
            Intent intent = new Intent(this, ChangeCredentialsActivity.class);
            intent.putExtra("EXTRA", "openChangePassword");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_gallery:
                fragment = new GalleryFragment();
                break;
            case R.id.nav_about_us:
                fragment = new AboutUsFragment();
                break;
            case R.id.nav_timeline:
                fragment = new TimelineFragment();
                break;
            case R.id.nav_team:
                fragment = new TeamFragment();
                break;
            case R.id.nav_donate_now:
                fragment = new DonateFragment();
                break;
            case R.id.nav_share:
                fragment = new ShareFragment();
                break;
            case R.id.nav_videos:
                fragment = new VideosFragment();
                break;
            case R.id.nav_contact_us:
                fragment = new ContactFragment();
                break;
            case R.id.nav_profile:
                Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    private void setHeaderData() {
        if (preferences.getBoolean(Constant.LOGIN_STATUS, false)) {
            //logged in
            String full_name = preferences.getString(Constant.FIRST_NAME, "") + " " + preferences.getString(Constant.LAST_NAME, "");
            name.setText(full_name);
            email.setText(preferences.getString(Constant.EMAIL, ""));
            Glide.with(this)
                    .load(preferences.getString(Constant.IMG_URL, ""))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.ic_account_circle_black_48dp)
                    .into(circleImageView);
        } else {
            //Not logged in
            name.setText(getResources().getString(R.string.app_name));
            email.setText(getResources().getString(R.string.saar_email));
            circleImageView.setImageResource(R.drawable.ic_account_circle_black_48dp);
        }
    }
}
