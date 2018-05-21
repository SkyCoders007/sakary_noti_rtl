package com.mxi.android.salarynotification.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.database.SQLiteTD;
import com.mxi.android.salarynotification.network.AppController;
import com.mxi.android.salarynotification.network.CommanClass;
import com.mxi.android.salarynotification.network.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {


    ProgressDialog pDialog;
    EditText et_emailid, et_company_name, et_emp_id, et_username, et_password;
    TextView tv_submit, tv_register;
    CommanClass cc;
    RelativeLayout relativeLayout;
    SQLiteTD dbcom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        cc = new CommanClass(this);
        dbcom = new SQLiteTD(SignUpActivity.this);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_activity_sign_up);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        et_emailid = (EditText) findViewById(R.id.et_email_sign_up);
        et_company_name = (EditText) findViewById(R.id.et_company_name_sign_up);
        et_emp_id = (EditText) findViewById(R.id.et_employment_id);
        et_username = (EditText) findViewById(R.id.et_username_sign_up);
        et_password = (EditText) findViewById(R.id.et_password_sign_up);
        tv_register = (TextView) findViewById(R.id.tv_register);
        String device_id = Settings.Secure.getString(SignUpActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        cc.savePrefString("device_id", device_id);


        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()) {

                    SignupWSCall();

                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private boolean checkValidation() {
        boolean isValidate = false;

        if (et_emailid.getText().toString().isEmpty()) {
            et_emailid.setError(getResources().getString(R.string.enter_email));
        } else if (et_company_name.getText().toString().isEmpty()) {
            et_company_name.setError(getResources().getString(R.string.enter_company_name));
        } else if (et_emp_id.getText().toString().isEmpty()) {
            et_emp_id.setError(getResources().getString(R.string.enter_emp_id));
        } else if (et_username.getText().toString().isEmpty()) {
            et_username.setError(getResources().getString(R.string.enter_user_name));
        } else if (et_password.getText().toString().isEmpty()) {
            et_password.setError(getResources().getString(R.string.enter_password));
        } else {
            isValidate = true;
        }

        return isValidate;
    }

    private void SignupWSCall() {
        String email = et_emailid.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String company_name = et_company_name.getText().toString().trim();
        String emp_id = et_emp_id.getText().toString().trim();
        String username = et_username.getText().toString().trim();

        if (!cc.isConnectingToInternet()) {
            cc.showSnackbar(relativeLayout, getString(R.string.no_internet));
        } else {
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.show();
            makeJsonsignup(email, password, company_name, username, emp_id);
        }


    }

    private void makeJsonsignup(final String email, final String password, final String company_name, final String username, final String emp_id) {
        AppController.getInstance().getRequestQueue().getCache().clear();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, URL.Url_registration,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("registration", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {

                                cc.savePrefString("email", email);
                                cc.savePrefString("comp_name", company_name);
                                cc.savePrefString("emp_id", emp_id);
                                cc.savePrefString("username", username);

                                dbcom.inseartSignup(email, username, password, company_name, emp_id, cc.loadPrefString("emp-token"));

                                JSONArray data = jsonObject.getJSONArray("data");
                                Log.e("JsonLArrayLength", data.length() + "");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObjectData = data.getJSONObject(i);
                                    Log.e("i", i + "");
                                    dbcom.inseartSearchData(jsonObjectData.getString("h_id"), jsonObjectData.getString("sal_year"), jsonObjectData.getString("sal_month"),
                                            jsonObjectData.getString("emp_id"), jsonObjectData.getString("empTypeID"), jsonObjectData.getString("Basic_Salary_standard"),
                                            jsonObjectData.getString("Basic_Salary_Current"), jsonObjectData.getString("Net"), jsonObjectData.getString("All_Allowances"),
                                            jsonObjectData.getString("All_Deductions"), jsonObjectData.getString("userid"),
                                            jsonObjectData.getString("dep_id"), jsonObjectData.getString("sec_id"), jsonObjectData.getString("degree_id"),
                                            jsonObjectData.getString("step_id"), jsonObjectData.getString("job_id"), jsonObjectData.getString("education_id"),
                                            jsonObjectData.getString("Notes"), jsonObjectData.getString("a_Marrige"), jsonObjectData.getString("a_Kids"),
                                            jsonObjectData.getString("a_Education"), jsonObjectData.getString("a_EducationHigh"),
                                            jsonObjectData.getString("a_Profissional"), jsonObjectData.getString("a_Risk"), jsonObjectData.getString("a_Transportation"),
                                            jsonObjectData.getString("a_Engineering"), jsonObjectData.getString("a_position"), jsonObjectData.getString("a_Diffrences"),
                                            jsonObjectData.getString("d_tax"), jsonObjectData.getString("d_Retire"), jsonObjectData.getString("d_Loan"),
                                            jsonObjectData.getString("d_compernsations"), jsonObjectData.getString("d_insuracne"), jsonObjectData.getString("d_Social_solidarity"),
                                            jsonObjectData.getString("d_vacations"), jsonObjectData.getString("d_TakeOff"), jsonObjectData.getString("d_Properity"),
                                            jsonObjectData.getString("d_absent"), jsonObjectData.getString("d_laber"), jsonObjectData.getString("Mobile_no"),
                                            jsonObjectData.getString("risk_descr"));

                                }
                                cc.savePrefBoolean("islogin", true);

                                Intent mIntent = new Intent(SignUpActivity.this,
                                        MainActivity.class);
                                startActivity(mIntent);
                                finish();

                            } else {

                                cc.showToast(jsonObject.getString("msg"));
                            }

                        } catch (JSONException e) {
                            //e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Log.e("Url_registration_ERROR", error.toString());
                cc.showSnackbar(relativeLayout, getString(R.string.ws_error));
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("device_id", cc.loadPrefString("device_id"));
                params.put("comp_name", company_name);
                params.put("emp_id", emp_id);
                params.put("username", username);

                Log.i("request_SignUp", params.toString());

                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                try {
                    String mip_token = response.headers.get("emp-token");
                    // Log.e("emp-token", mip_token);
                    cc.savePrefString("emp-token", mip_token);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);

            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");

    }

}
