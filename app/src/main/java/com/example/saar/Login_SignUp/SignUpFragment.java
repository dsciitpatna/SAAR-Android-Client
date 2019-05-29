package com.example.saar.Login_SignUp;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.saar.R;

import java.util.Calendar;

public class SignUpFragment extends Fragment {

    EditText dobEditText;
    Spinner spinnerGraduationYear;
    Spinner spinnerDegree;
    Spinner spinnerDepartment;
    Spinner spinnerEmploymentType;
    DatePickerDialog.OnDateSetListener setListener;
    int year, month, day;

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerEmploymentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                String date = day + "/" + month + "/" + year;
                dobEditText.setText(date);
            }
        };
    }
}
