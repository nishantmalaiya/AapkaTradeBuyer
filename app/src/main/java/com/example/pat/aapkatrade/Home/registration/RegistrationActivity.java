package com.example.pat.aapkatrade.Home.registration;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pat.aapkatrade.Home.HomeActivity;
import com.example.pat.aapkatrade.Home.registration.entity.BuyerRegistration;
import com.example.pat.aapkatrade.Home.registration.entity.City;
import com.example.pat.aapkatrade.Home.registration.spinner_adapter.SpCityAdapter;
import com.example.pat.aapkatrade.R;
import com.example.pat.aapkatrade.general.AppSharedPreference;
import com.example.pat.aapkatrade.general.App_config;
import com.example.pat.aapkatrade.general.Utils.AndroidUtils;
import com.example.pat.aapkatrade.general.Validation;
import com.example.pat.aapkatrade.general.progressbar.ProgressBarHandler;
import com.example.pat.aapkatrade.login.ActivityOTPVerify;
import com.example.pat.aapkatrade.user_dashboard.add_product.CustomSpinnerAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static BuyerRegistration formBuyerData = new BuyerRegistration();
    private int isAllFieldSet = 0;
    private Spinner spState, spCity;
    private EditText etFirstName, etLastName, etDOB, etEmail, etMobileNo, etAddress, etPassword, etReenterPassword;
    private TextView tvSave, tv_agreement;
    private LinearLayout registrationLayout;
    private ArrayList<String> stateList = new ArrayList<>();
    private ArrayList<City> cityList = new ArrayList<>();
    private ImageView openCalander;
    private AppSharedPreference appSharedPreference;
    private String stateID = "", cityID = "";
    private DatePickerDialog datePickerDialog;
    private ProgressBarHandler progressBarHandler;
    private Context context;
    private CheckBox agreement_check;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = RegistrationActivity.this;
        appSharedPreference = new AppSharedPreference(RegistrationActivity.this);
        setUpToolBar();
        initView();

        getState();


        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("reach", "reach3");
                getBuyerFormData();
                validateFields();
                if (isAllFieldSet == 0) {
                    callWebServiceForBuyerRegistration();

                }
            }


        });

        openCalander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegistrationActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.show(getFragmentManager(), "DatePickerDialog");

            }
        });


    }

    private void showDate(int year, int month, int day) {
        etDOB.setTextColor(getResources().getColor(R.color.black));
        etDOB.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }


    private void callWebServiceForBuyerRegistration() {
        Log.e("reach", " Buyer Data--------->\n" + formBuyerData.toString());
        progressBarHandler.show();
        Ion.with(RegistrationActivity.this)
                .load(getResources().getString(R.string.webservice_base_url) + "/buyerregister")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "1")
                .setBodyParameter("country_id", formBuyerData.getCountryId())
                .setBodyParameter("state_id", formBuyerData.getStateId())
                .setBodyParameter("city_id", formBuyerData.getCityId())
                .setBodyParameter("address", formBuyerData.getAddress())
                .setBodyParameter("name", formBuyerData.getFirstName())
                .setBodyParameter("lastname", formBuyerData.getLastName())
                .setBodyParameter("email", formBuyerData.getEmail())
                .setBodyParameter("mobile", formBuyerData.getMobile())
                .setBodyParameter("password", formBuyerData.getPassword())
                .setBodyParameter("client_id", formBuyerData.getClientId())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {

                            if (result.get("error").getAsString().equals("false")) {
                                Log.e("registration_buyer", result.toString());
                                AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());

                                progressBarHandler.hide();
                                Intent call_to_startactivity = new Intent(RegistrationActivity.this, ActivityOTPVerify.class);
                                call_to_startactivity.putExtra("class_name", context.getClass().getName());
                                startActivity(call_to_startactivity);

                            } else {

                                progressBarHandler.hide();
                                AndroidUtils.showSnackBar(registrationLayout, result.get("message").getAsString());


                            }
                        } else {

                            Log.e("result_seller_error", e.toString());
                            showmessage(e.toString());
                        }
                    }

                });
    }


    private void getState() {
        stateList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.state_list)));
        CustomSpinnerAdapter spinnerArrayAdapter = new CustomSpinnerAdapter(context, stateList);
        spState.setAdapter(spinnerArrayAdapter);
        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                cityList = new ArrayList<>();
                AndroidUtils.showErrorLog(context, "State Id is ::::::::  " + position);
                if (position > 0)
                    getCity(String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getCity(String stateId) {
        progressBarHandler.show();
        findViewById(R.id.input_layout_city).setVisibility(View.VISIBLE);
        Ion.with(context)
                .load("http://aapkatrade.com/slim/dropdown")
                .setHeader("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("authorization", "xvfdbgfdhbfdhtrh54654h54ygdgerwer3")
                .setBodyParameter("type", "city")
                .setBodyParameter("id", stateId)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressBarHandler.hide();
                        Log.e("city result ", result == null ? "null" : result.toString());

                        if (result != null) {
                            JsonArray jsonResultArray = result.getAsJsonArray("result");

                            City cityEntity_init = new City("-1", "Please Select City");
                            cityList.add(cityEntity_init);

                            for (int i = 0; i < jsonResultArray.size(); i++) {
                                JsonObject jsonObject1 = (JsonObject) jsonResultArray.get(i);
                                City cityEntity = new City(jsonObject1.get("id").getAsString(), jsonObject1.get("name").getAsString());
                                cityList.add(cityEntity);
                            }

                            SpCityAdapter spCityAdapter = new SpCityAdapter(context, cityList);
                            spCity.setAdapter(spCityAdapter);

                            spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    cityID = cityList.get(position).cityId;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
                            showmessage("No City Found");
                        }
                    }

                });

    }


    private void setUpToolBar() {
        ImageView homeIcon = (ImageView) findViewById(R.id.iconHome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AndroidUtils.setImageColor(homeIcon, context, R.color.white);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        progressBarHandler = new ProgressBarHandler(this);
        registrationLayout = (LinearLayout) findViewById(R.id.registrationLayout);
        spState = (Spinner) findViewById(R.id.spStateCategory);
        spCity = (Spinner) findViewById(R.id.spCityCategory);
        tvSave = (TextView) findViewById(R.id.tvSave);
        tvSave.setText(getString(R.string.save));

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etDOB = (EditText) findViewById(R.id.etDOB);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobileNo = (EditText) findViewById(R.id.etMobileNo);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etReenterPassword = (EditText) findViewById(R.id.etReenterPassword);

        openCalander = (ImageView) findViewById(R.id.openCalander);
        agreement_check = (CheckBox) findViewById(R.id.agreement_check);
        tv_agreement = (TextView) findViewById(R.id.tv_agreement);
        tv_agreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agreement_check.isChecked()) {
                    agreement_check.setChecked(false);
                } else {
                    agreement_check.setChecked(true);
                }
            }
        });


    }

    private void validateFields() {
        isAllFieldSet = 0;
        Log.e("reach", "validateFiledsCalled");


        if (formBuyerData != null) {
            if (Validation.isEmptyStr(formBuyerData.getFirstName())) {
                putError(0);
                isAllFieldSet++;
            } else if (Validation.isEmptyStr(formBuyerData.getLastName())) {
                putError(1);
                isAllFieldSet++;
            } else if (!Validation.isValidNumber(formBuyerData.getMobile(), Validation.getNumberPrefix(formBuyerData.getMobile()))) {
                putError(3);
                isAllFieldSet++;
            } else if (Validation.isValidDOB(etDOB.getText().toString())) {
                putError(6);
            } else if (spState != null && spState.getSelectedItemPosition() == 0) {
                showmessage("Please Select State");
                isAllFieldSet++;
            } else if (spCity.getSelectedItemPosition() == 0) {
                showmessage("Please Select City");
                isAllFieldSet++;
            } else if (Validation.isEmptyStr(formBuyerData.getAddress())) {
                putError(9);
                isAllFieldSet++;
            } else if (!Validation.isValidEmail(formBuyerData.getEmail())) {
                putError(2);
                isAllFieldSet++;
            } else if (!Validation.isEmptyStr(formBuyerData.getUserName())) {
                putError(10);
                isAllFieldSet++;
            } else if (!Validation.isValidPassword(formBuyerData.getPassword())) {
                putError(4);
                isAllFieldSet++;
            } else if (!Validation.isValidPassword(formBuyerData.getConfirmPassword())) {
                putError(15);
                isAllFieldSet++;
            } else if (!Validation.isPasswordMatching(formBuyerData.getPassword(), formBuyerData.getConfirmPassword())) {
                putError(5);
                isAllFieldSet++;
            } else if (!agreement_check.isChecked()) {
                putError(16);
                isAllFieldSet++;
            }
        }
    }

    private void putError(int id) {
        Log.e("reach", "       )))))))))" + id);
        switch (id) {
            case 0:
                etFirstName.setError("First Name Can't be empty");
                showmessage("First Name Can't be empty");
                break;
            case 1:
                etLastName.setError("Last Name Can't be empty");
                showmessage("Last Name Can't be empty");
                break;
            case 2:
                etEmail.setError("Please Enter Valid Email");
                showmessage("Please Enter Valid Email");
                break;
            case 3:
                etMobileNo.setError("Please Enter Valid Mobile Number");
                showmessage("Please Enter Valid Mobile Number");
                break;
            case 4:
                etPassword.setError(getResources().getString(R.string.password_validing_text));
                showmessage(getResources().getString(R.string.password_validing_text));
                break;
            case 5:
                etReenterPassword.setError("Password did not match");
                showmessage("Password did not match");
                break;
            case 6:
                //showmessage("Please Select Date");
                break;
            case 9:
                etAddress.setError("Address Can't be empty");
                showmessage("Address Can't be empty");
                break;
            case 10:
                showmessage("Please Enter Valid UserName");
                break;

            case 15:
                etReenterPassword.setError(getResources().getString(R.string.password_validing_text));
                showmessage(getResources().getString(R.string.password_validing_text));
                break;
            case 16:
                showmessage("Please Check the Agreement");
                break;

            default:
                break;
        }
    }

    public void showmessage(String message) {
        AndroidUtils.showSnackBar(registrationLayout, message);
    }


    public void getBuyerFormData() {
        formBuyerData.setStateId(stateID == null ? "" : stateID);
        formBuyerData.setCityId(cityID == null ? "" : cityID);
        formBuyerData.setAddress(etAddress.getText().toString());
        formBuyerData.setFirstName(etFirstName.getText().toString());
        formBuyerData.setLastName(etLastName.getText().toString());
        formBuyerData.setEmail(etEmail.getText().toString());
        formBuyerData.setMobile(etMobileNo.getText().toString());
        formBuyerData.setPassword(etPassword.getText().toString());
        formBuyerData.setConfirmPassword(etReenterPassword.getText().toString());
        formBuyerData.setClientId(App_config.getCurrentDeviceId(RegistrationActivity.this));
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        showDate(year, monthOfYear + 1, dayOfMonth);
    }
}