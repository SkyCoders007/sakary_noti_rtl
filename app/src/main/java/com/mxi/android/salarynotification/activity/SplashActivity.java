package com.mxi.android.salarynotification.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
import java.util.Locale;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressbar;
    CommanClass cc;
    SQLiteTD dbcom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setLanguage();
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        cc = new CommanClass(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // only for marshmallow and newer versions
            checkReadWritePermission();
        } else {

            if (cc.loadPrefBoolean("islogin")) {
                if (cc.isConnectingToInternet()) {
                    Log.e("islogin", "_create");
                    makeJsonCall();
                } else {
                    CountDown();
                }
            } else {
                startActivity(new Intent(SplashActivity.this,
                        LoginActivity.class));
                finish();
            }
        }

        // CountDown();

    }

    public void setLanguage() {
        String languageToLoad = "ar";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // CountDown();
                    if (cc.loadPrefBoolean("islogin")) {
                        if (cc.isConnectingToInternet()) {
                            Log.e("islogin", "OnRequestResult");
                            makeJsonCall();
                        } else {
                            CountDown();
                        }
                    } else {
                        startActivity(new Intent(SplashActivity.this,
                                LoginActivity.class));
                        finish();
                    }

                } else {
                    //  Toast.makeText(NewPrescriptionRequest.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
                    showErrorDialog("Please allow the permission for better performance", true);
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void checkReadWritePermission() {
        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                return;
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        } else {
            // CountDown();
            if (cc.loadPrefBoolean("islogin")) {
                if (cc.isConnectingToInternet()) {
                    Log.e("islogin", "PermissionCheck");
                    makeJsonCall();
                } else {
                    CountDown();
                }
            } else {
                startActivity(new Intent(SplashActivity.this,
                        LoginActivity.class));
                finish();
            }
        }
    }

    public void showErrorDialog(String msg, final boolean isFromPermission) {
        progressbar.setVisibility(View.INVISIBLE);
        AlertDialog.Builder alert = new AlertDialog.Builder(SplashActivity.this);
        alert.setTitle("Salary Notification");
        alert.setMessage(msg);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (isFromPermission) {
                    checkReadWritePermission();
                } else {
                    dialog.dismiss();
                }
            }
        });
        alert.show();
    }

    public void CountDown() {
        CountDownTimer mCountDownTimer = new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub

                progressbar.setVisibility(View.INVISIBLE);
                Intent mIntent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(mIntent);
                finish();
            }
        };
        mCountDownTimer.start();
    }

    private void makeJsonCall() {
        AppController.getInstance().getRequestQueue().getCache().clear();
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, URL.Url_search,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("response_search", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            progressbar.setVisibility(View.INVISIBLE);
                            dbcom = new SQLiteTD(SplashActivity.this);

                            if (jsonObject.getString("status").equals("200")) {

                                if (jsonObject.getBoolean("has_new_record")) {

                                    cc.fireNotification(jsonObject.getString("last_month"), jsonObject.getString("last_year"));
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

                                Intent mIntent = new Intent(SplashActivity.this,
                                        MainActivity.class);
                                startActivity(mIntent);
                                finish();

                            } else if (jsonObject.getString("status").equals("498")) {
                                cc.showToast(getString(R.string.wrong_username_pass));
                            } else {
                                Intent mIntent = new Intent(SplashActivity.this,
                                        MainActivity.class);
                                startActivity(mIntent);
                                finish();
                                //  cc.showToast(jsonObject.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSONException_search", e.toString());
                        }

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //  pDialog.dismiss();
                progressbar.setVisibility(View.INVISIBLE);
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

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");


    }

}