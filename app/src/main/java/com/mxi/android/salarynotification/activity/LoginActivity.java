package com.mxi.android.salarynotification.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog pDialog;
    EditText et_username, et_password;
    CommanClass cc;
    TextView tv_login, tv_signup, tv_forgot_password;
    RelativeLayout relativeLayout;
    SQLiteTD dbcom;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cc = new CommanClass(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_activity_login);
        tv_login = (TextView) findViewById(R.id.tv_login);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);

        dbcom = new SQLiteTD(LoginActivity.this);
        String device_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        cc.savePrefString("device_id", device_id);
        /*tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginService();
            *//*    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();*//*
            }
        });*/

//        makeJsonlogin();

        tv_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                loginService();
                return false;
            }
        });
        tv_signup.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_signup:
                loginService();
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
                break;
            case R.id.tv_forgot_password:
                ForgotPassword();
                break;
        }

    }

    private void ForgotPassword() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.forgot_password, null);
        dialogBuilder.setView(dialogView);

        final EditText et_emp_id = (EditText) dialogView.findViewById(R.id.et_emp_id);
        final EditText et_username = (EditText) dialogView.findViewById(R.id.et_username);
        TextView tv_login = (TextView) dialogView.findViewById(R.id.tv_login);
        final AlertDialog alertDialog = dialogBuilder.create();

        tv_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String emp_id = et_emp_id.getText().toString().trim();
                String user_name = et_username.getText().toString().trim();
                if (!cc.isConnectingToInternet()) {
                    cc.showSnackbar(relativeLayout, getString(R.string.no_internet));
                } else if (et_emp_id.getText().toString().isEmpty()) {
                    et_emp_id.setError(getResources().getString(R.string.enter_emp_id));
                } else if (et_username.getText().toString().isEmpty()) {
                    et_username.setError(getResources().getString(R.string.enter_user_name));
                } else {
                    pDialog = new ProgressDialog(LoginActivity.this);
                    pDialog.setMessage(getString(R.string.loading));
                    pDialog.show();
                    makeJsonForgotPassword(emp_id, user_name);
                    alertDialog.dismiss();
                }

                return false;
            }
        });
        alertDialog.show();
    }

    private void loginService() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        /*if (!cc.isConnectingToInternet()) {
            cc.showSnackbar(relativeLayout, getString(R.string.no_internet));
        } else */
        if (username.equals("")) {
            cc.showSnackbar(relativeLayout, getString(R.string.enter_email));
        } else if (password.equals("")) {
            cc.showSnackbar(relativeLayout, getString(R.string.enter_password));
        } else {
           /* pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.show();*/
            // makeJsonlogin(email, password);
            try {
                c = dbcom.Login(username, password);
                if (c.getCount() != 0 && c != null) {

                    c.moveToFirst();
                    do {
                        if (!username.equals(c.getString(2))) {
                            // pDialog.dismiss();
                            cc.showSnackbar(relativeLayout, getString(R.string.enter_wrong_username));
                        } else if (!password.equals(c.getString(3))) {
                            // pDialog.dismiss();
                            cc.showSnackbar(relativeLayout, getString(R.string.enter_wrong_password));
                        } else {
                            // pDialog.dismiss();
                            cc.savePrefString("emp-token", c.getString(6));
                            cc.savePrefString("emp_id", c.getString(5));


                            if (cc.isConnectingToInternet()) {
                                makeJsonCall();
                            } else {
                                cc.savePrefBoolean("islogin", true);
                                Intent mIntent = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(mIntent);
                                finish();

                            }


                        }


                    } while (c.moveToNext());

                } else {
                    cc.showSnackbar(relativeLayout, getString(R.string.wrong_username_pass));
                }

            } catch (Exception e) {
                e.printStackTrace();
                cc.showSnackbar(relativeLayout, getString(R.string.ws_error));

            }

        }
    }


    private void makeJsonCall() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setTitle(getResources().getString(R.string.loading));
        pDialog.show();
                AppController.getInstance().getRequestQueue().getCache().clear();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, URL.Url_search,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("response_search", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            dbcom = new SQLiteTD(LoginActivity.this);


                            if (jsonObject.getString("status").equals("200")) {

                                /*"status":"498","msg":"Token authorization required"*/

                                pDialog.dismiss();

                                if (jsonObject.getBoolean("has_new_record")) {
                                    cc.fireNotification(jsonObject.getString("last_month"),jsonObject.getString("last_year"));
                                }

                                JSONArray data = jsonObject.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject jsonObjectData = data.getJSONObject(i);

                                    dbcom.inseartORreplace(jsonObjectData.getString("h_id"), jsonObjectData.getString("sal_year"), jsonObjectData.getString("sal_month"),
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

                                Intent mIntent = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(mIntent);
                                finish();

                            } else if (jsonObject.getString("status").equals("498")) {
                                cc.showSnackbar(relativeLayout, getString(R.string.wrong_username_pass));
                                pDialog.dismiss();
                            } else {

                                cc.savePrefBoolean("islogin", true);

                                Intent mIntent = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(mIntent);
                                finish();
//                                cc.showToast(jsonObject.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.e("JSONException_search", e.toString());
                        }
//                        pDialog.dismiss();
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                cc.showToast(getString(R.string.ws_error));
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("emp-token", cc.loadPrefString("emp-token"));
                Log.i("request header", headers.toString());
                return headers;
            }

        };

//        AppController.getInstance().getRequestQueue().getCache().clear();

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");

    }


    private void makeJsonlogin(final String email, final String password) {
//    private void makeJsonlogin() {

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, URL.Url_temp,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("@@@@@@Temp@@@@@", response);
                        jsonParseMatchList(response);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // pDialog.dismiss();
                cc.showSnackbar(relativeLayout, getString(R.string.ws_error));
            }
        }) {
/*
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", email);
                params.put("password", password);
                params.put("device_id", cc.loadPrefString("device_id"));
                //params.put("comp_name", "android");
                // params.put("emp_id", cc.loadPrefString("device_id"));
                // params.put("username", "User");

                Log.i("request login", params.toString());

                return params;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                try {
                    String mip_token = response.headers.get("emp-token");
                    Log.e("emp-token", mip_token);
                    cc.savePrefString("emp-token", mip_token);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }*/

        };
/*        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");

    }

    private void jsonParseMatchList(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            Log.e("login_response", response + "");
            // pDialog.dismiss();
//            Log.e("@@@@UserName",cc.MyText(jsonObject.getString("username")));


            if (jsonObject.getString("has_new_record").equalsIgnoreCase("true")) {
                cc.fireNotification(jsonObject.getString("last_month"),jsonObject.getString("last_year"));

            }

            if (jsonObject.getString("status").equals("200")) {

                JSONObject user = jsonObject.getJSONObject("user");

                cc.savePrefString("email", user.getString("email"));
                cc.savePrefString("comp_name", user.getString("comp_name"));
                cc.savePrefString("emp_id", user.getString("emp_id"));
                cc.savePrefString("username", user.getString("username"));
                cc.savePrefString("is_confirm", user.getString("is_confirm"));

                JSONArray data = jsonObject.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObjectData = data.getJSONObject(i);

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

                Intent mIntent = new Intent(LoginActivity.this,
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

    private void makeJsonForgotPassword(final String emp_id, final String username) {
        AppController.getInstance().getRequestQueue().getCache().clear();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, URL.Url_forgotpassword,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("ForgotPassword", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            pDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {

                                cc.showToast(jsonObject.getString("msg"));
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
                // Log.e("Url_registration_ERROR", error.toString());
                cc.showSnackbar(relativeLayout, getString(R.string.ws_error));
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emp_id", emp_id);
                params.put("username", username);

                Log.i("request_forgotpassword", params.toString());

                return params;
            }


        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");

    }

}
