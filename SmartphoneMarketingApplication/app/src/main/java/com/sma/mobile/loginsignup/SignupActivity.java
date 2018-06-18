package com.sma.mobile.loginsignup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.home.HomeActivity;
import com.sma.mobile.utils.firebasenotifications.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class SignupActivity extends AbstractAppCompatActivity {

    private static final String TAG = SignupActivity.class.getName();

    @BindView(R.id.edit_text_email_id)
    EditText editTextEmail;
    @BindView(R.id.edit_text_date_of_birth_id)
    Spinner spinnerDateOfBirth;
    @BindView(R.id.edit_text_location_id)
    Spinner spinnerLocation;
    @BindView(R.id.radio_group_id)
    RadioGroup radioGroup;
    @BindView(R.id.edit_text_invite_code_id)
    EditText editTextInviteCode;
    @BindView(R.id.btn_signup)
    Button buttonSignUp;
    @BindView(R.id.link_login)
    TextView textViewLoginLink;
    private String correspondingCode = "VN-HN";

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        textViewLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        List<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1900; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, years);
        spinnerDateOfBirth.setAdapter(adapter);
        spinnerDateOfBirth.setSelection(2017 - 1900);

        Resources r = getResources();
        TypedArray citiesCodes = r.obtainTypedArray(R.array.vietnamese_cities_and_provinces_with_vietnamese_characters);
        List<String> city = new ArrayList<String>();
        List<String> code = new ArrayList<String>();
        int cpt = citiesCodes.length();
        String[] cityAndCode = r.getStringArray(R.array.vietnamese_cities_and_provinces_with_vietnamese_characters);
        for (int i = 0; i < cpt; ++i) {
            if (i % 2 == 0) {
                code.add(cityAndCode[i]);
            } else {
                city.add(cityAndCode[i]);
            }
        }
        citiesCodes.recycle();
        final List<String> fCode = code;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, city);
        spinnerLocation.setAdapter(dataAdapter);
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();
                correspondingCode = fCode.get(index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public void signUp() {
        showProcessing();
        Log.d(TAG, "Signup");
        String gender = "Male";
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.radio_button_male_id) {
            gender = "Male";
        } else if (checkedRadioButtonId == R.id.radio_button_female) {
            gender = "Female";
        }

        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().updateUserInfo(registrationToken, editTextEmail.getText().toString(),
                gender, spinnerDateOfBirth.getSelectedItem().toString(), correspondingCode, editTextInviteCode.getText().toString(),
                new JCallback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        hideProcessing();
                        if (response.code() == 200) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            getMaterialDialogAlert(SignupActivity.this, "Thông báo", "Mã giới thiệu không hợp lệ.").show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        hideProcessing();
                    }
                });
    }


    public void onSignupSuccess() {
        buttonSignUp.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        buttonSignUp.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        return valid;
    }
}