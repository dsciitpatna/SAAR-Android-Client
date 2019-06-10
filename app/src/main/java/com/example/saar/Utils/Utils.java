package com.example.saar.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.saar.Constant;

public class Utils {

    public static void closeKeyboard(View view, Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null)
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    public static void resetSharedPreferences(SharedPreferences preferences, SharedPreferences.Editor editor) {
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
    }

}
