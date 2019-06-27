package com.example.saar.Login_SignUp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.saar.Constant;
import com.example.saar.OtpActivity;
import com.example.saar.R;
import com.example.saar.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;
import timber.log.Timber;

public class SignUpFragment extends Fragment {

    EditText dobEditText, first_name_text, last_name_text, rollno_text, email_text, phone_text, fb_link_text, linkedin_link_text, password_text;
    EditText confirm_password_text, present_employer_text, designation_text, address_text, country_text, state_text, city_text, achievements_text;
    TextView errorsDisplay;
    Spinner spinnerGraduationYear, spinnerEmploymentType, spinnerDegree, spinnerDepartment;
    DatePickerDialog.OnDateSetListener setListener;
    Button signupButton, errorOkButton;
    int year, month, day;
    String rollno, first_name, last_name, email, phone, fb_link, linkedin_link, password, confirm_password, dob, graduation_year, degree, department;
    String employment_type, present_employer, designation, address, country, state, city, achievements;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        spinnerGraduationYear = (Spinner) rootView.findViewById(R.id.spinner_graduation_year);
        spinnerDegree = (Spinner) rootView.findViewById(R.id.spinner_degree);
        spinnerDepartment = (Spinner) rootView.findViewById(R.id.spinner_department);
        spinnerEmploymentType = (Spinner) rootView.findViewById(R.id.spinner_employment_type);

        // Initializing ArrayAdapter
        ArrayAdapter<CharSequence> spinnerGraduationYearArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.graduation_year_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> spinnerDegreeArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.degree_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> spinnerDepartmentArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.department_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> spinnerEmploymentTypeArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.employment_type_array, android.R.layout.simple_spinner_item);

        spinnerGraduationYearArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDegreeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmploymentTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGraduationYear.setAdapter(spinnerGraduationYearArrayAdapter);
        spinnerDegree.setAdapter(spinnerDegreeArrayAdapter);
        spinnerDepartment.setAdapter(spinnerDepartmentArrayAdapter);
        spinnerEmploymentType.setAdapter(spinnerEmploymentTypeArrayAdapter);

        dobEditText = (EditText) rootView.findViewById(R.id.dob_edit);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        //referencing the views
        signupButton = rootView.findViewById(R.id.signup_button);
        rollno_text = rootView.findViewById(R.id.college_roll_number_edit);
        first_name_text = rootView.findViewById(R.id.first_name_edit);
        last_name_text = rootView.findViewById(R.id.last_name_edit);
        email_text = rootView.findViewById(R.id.email_edit);
        phone_text = rootView.findViewById(R.id.contact_no_edit);
        fb_link_text = rootView.findViewById(R.id.fb_profile_link_edit);
        linkedin_link_text = rootView.findViewById(R.id.linkedin_profile_link_edit);
        password_text = rootView.findViewById(R.id.password_edit);
        confirm_password_text = rootView.findViewById(R.id.confirm_password_edit);
        present_employer_text = rootView.findViewById(R.id.present_employer_educational_inst_edit);
        designation_text = rootView.findViewById(R.id.designation_edit);
        address_text = rootView.findViewById(R.id.address_edit);
        country_text = rootView.findViewById(R.id.country_edit);
        state_text = rootView.findViewById(R.id.state_edit);
        city_text = rootView.findViewById(R.id.city_edit);
        achievements_text = rootView.findViewById(R.id.achievements_edit);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(R.string.saar_signup);

        spinnerGraduationYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                graduation_year = spinnerGraduationYear.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degree = spinnerDegree.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = spinnerDepartment.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerEmploymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employment_type = spinnerEmploymentType.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth + "/" + month + "/" + year;
                dobEditText.setText(date);
                dob = date;
            }
        };

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatas();
            }
        });

    }

    private void getDatas() {
        //Collecting all the datas
        rollno = rollno_text.getText().toString();
        first_name = first_name_text.getText().toString();
        last_name = last_name_text.getText().toString();
        email = email_text.getText().toString();
        phone = phone_text.getText().toString();
        fb_link = fb_link_text.getText().toString();
        linkedin_link = linkedin_link_text.getText().toString();
        password = password_text.getText().toString();
        confirm_password = confirm_password_text.getText().toString();
        present_employer = present_employer_text.getText().toString();
        designation = designation_text.getText().toString();
        address = address_text.getText().toString();
        country = country_text.getText().toString();
        state = state_text.getText().toString();
        city = city_text.getText().toString();
        achievements = achievements_text.getText().toString();

        if (rollno.isEmpty() || first_name.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                password.isEmpty() || confirm_password.isEmpty()) {
            Toast.makeText(getContext(), getResources().getString(R.string.empty_edittext_in_signup), Toast.LENGTH_LONG).show();
        } else if (Utils.isNetworkConnected(getContext())) {
            registerUser();
        } else {
            Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }

    }

    private void registerUser() {
        //closing soft Keyboard using Utils class method
        Utils.closeKeyboard(getView(), getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.signup_progress));
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.SIGNUP_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Timber.d(response);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int status = Integer.parseInt(jsonObject.getString("status"));
                    if (status == 200) {
                        Timber.d(getString(R.string.signup_successfull));
                        Toast.makeText(getContext(), getString(R.string.signup_successfull), Toast.LENGTH_LONG).show();
                        // Should redirect to OTP page.
                        // Put rollno value as a parameter in the intent. This value will be useful in verifying the otp.
                        Intent intent = new Intent(getActivity(), OtpActivity.class);
                        intent.putExtra("rollno", rollno);
                        startActivity(intent);

                    } else {

                        JSONArray jsonArray = jsonObject.getJSONArray("messages");
                        Timber.d(getString(R.string.signup_failed));
                        showDialog(getActivity(), jsonArray.toString());
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
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("fb_link", fb_link);
                params.put("linkedin_link", linkedin_link);
                params.put("password", password);
                params.put("confirm_password", confirm_password);
                params.put("dob", dob);
                params.put("graduation_year", graduation_year);
                params.put("degree", degree);
                params.put("department", department);
                params.put("employment_type", employment_type);
                params.put("present_employer", present_employer);
                params.put("designation", designation);
                params.put("address", address);
                params.put("country", country);
                params.put("state", state);
                params.put("city", city);
                params.put("achievements", achievements);
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

    public void showDialog(Context context, String mssg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.signup_error_dialog);

        errorsDisplay = dialog.findViewById(R.id.signup_error_text);
        errorsDisplay.setText(mssg);

        errorOkButton = dialog.findViewById(R.id.dialog_ok);
        errorOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
