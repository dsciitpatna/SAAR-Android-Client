package com.example.saar.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.saar.Constant;
import com.example.saar.Login_SignUp.LoginSignupActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class Utils {

    public static void closeKeyboard(View view, Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    public static void logout(SharedPreferences.Editor editor, Context context) {
        //setting all values of shared preferences to empty string
        //login status is set to false
        editor.putBoolean(Constant.LOGIN_STATUS, false);
        editor.putString(Constant.ROLLNO, "");
        editor.putString(Constant.FIRST_NAME, "");
        editor.putString(Constant.LAST_NAME, "");
        editor.putString(Constant.EMAIL, "");
        editor.putString(Constant.PHONE, "");
        editor.putString(Constant.FB_LINK, "");
        editor.putString(Constant.LINKEDIN_LINK, "");
        editor.putString(Constant.DOB, "");
        editor.putString(Constant.GRADUATION_YEAR, "");
        editor.putString(Constant.DEGREE, "");
        editor.putString(Constant.DEPARTMENT, "");
        editor.putString(Constant.EMPLOYEMENT_TYPE, "");
        editor.putString(Constant.PRESENT_EMPLOYER, "");
        editor.putString(Constant.DESIGNATION, "");
        editor.putString(Constant.ADDRESS, "");
        editor.putString(Constant.COUNTRY, "");
        editor.putString(Constant.CITY, "");
        editor.putString(Constant.STATE, "");
        editor.putString(Constant.ACHIEVEMENTS, "");
        editor.apply();
        Toast.makeText(context, "Logged Out", Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, LoginSignupActivity.class));
    }

    public static String getDepartment(String rollno) {
        String year = rollno.substring(0, 2);
        String dept = rollno.substring(4, 6);
        return dept + year;
    }

    public static String getBatch(String rollno) {
        String year = rollno.substring(0, 2);
        String category = rollno.substring(2, 4);
        String value = null;

        if (category.equals("01")) {
            value = "btech" + year;
        } else if (category.equals("21")) {
            value = "phd" + year;
        } else if (category.equals("11")) {
            value = "mtech" + year;
        } else if (category.equals("12")) {
            value = "msc" + year;
        } else {
            value = "unknown";
        }
        return value;
    }

    public static void unsuscribeFromNotification(String rollno) {
        if (rollno.length() == 8) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(getBatch(rollno));
            FirebaseMessaging.getInstance().unsubscribeFromTopic(getDepartment(rollno));
        }
    }

    //Function that returns whether the phone is connected to internet or not
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
