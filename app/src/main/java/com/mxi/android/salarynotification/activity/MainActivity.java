package com.mxi.android.salarynotification.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.database.SQLiteTD;
import com.mxi.android.salarynotification.model.search;
import com.mxi.android.salarynotification.network.AppController;
import com.mxi.android.salarynotification.network.CommanClass;
import com.mxi.android.salarynotification.network.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_last_reached_salary, tv_search_view;
    TextView tv_share_application, tv_export_history;
    TextView tv_leave_application, tv_logout, tv_about_us;

    CommanClass cc;
    RelativeLayout relativeLayout;
    ProgressDialog pDialog;

    SQLiteTD dbcom;
    Cursor c;
    public static ArrayList<search> salarylist;
    public static Boolean isSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cc = new CommanClass(this);
        dbcom = new SQLiteTD(MainActivity.this);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_activity_main);
        tv_last_reached_salary = (TextView) findViewById(R.id.tv_last_reached_salary);
        tv_search_view = (TextView) findViewById(R.id.tv_search_and_view);
        tv_share_application = (TextView) findViewById(R.id.tv_share_the_application);
        tv_export_history = (TextView) findViewById(R.id.tv_export_historical_records);
        tv_leave_application = (TextView) findViewById(R.id.tv_leave_application);
        tv_about_us = (TextView) findViewById(R.id.tv_about_us);
        tv_logout = (TextView) findViewById(R.id.tv_logout);

        initListeners();
        getDataFromWs();

    }

    private void initListeners() {
        tv_last_reached_salary.setOnClickListener(this);
        tv_search_view.setOnClickListener(this);
        tv_share_application.setOnClickListener(this);
        tv_export_history.setOnClickListener(this);
        tv_leave_application.setOnClickListener(this);
        tv_about_us.setOnClickListener(this);
        tv_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_last_reached_salary:
                isSearch = false;
                startActivity(new Intent(MainActivity.this, LastReachedSalaryActivity.class));
                break;
            case R.id.tv_search_and_view:
                isSearch = true;
                startActivity(new Intent(MainActivity.this, SearchViewActivity.class));
                break;
            case R.id.tv_share_the_application:
                shareApplication();
                break;
            case R.id.tv_export_historical_records:
                startActivity(new Intent(MainActivity.this,PdfActivity.class));
                break;
            case R.id.tv_leave_application:
                if(cc.isConnectingToInternet()){
                    leaveApplication();
                }else{
                    cc.showSnackbar(relativeLayout,getResources().getString(R.string.no_internet));
                }

                break;
            case R.id.tv_about_us:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                break;
            case R.id.tv_logout:
              /*  pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Please wait...");
                pDialog.show();*/

                cc.showToast(getString(R.string.user_logout));
                cc.logoutapp();
                Intent mIntent = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(mIntent);
                finish();
                /*if (cc.isConnectingToInternet()) {
                    makeJsonLogout();
                } else {
                    cc.showSnackbar(relativeLayout, getString(R.string.no_internet));
                }*/
                break;
        }

    }

    private void leaveApplication() {

        startActivity(new Intent(MainActivity.this,LeaveApplicationActivity.class));
        finish();
    }

    private void shareApplication() {
        StringBuffer sb;
        sb = new StringBuffer();
        sb.append("Salary Notification:\n");
        sb.append("Download app from the following app.\n");
        sb.append("https://goo.gl/Mlxsdt");

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_TEXT, sb.toString());

        startActivity(Intent.createChooser(share, "Share via"));
/*

        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(getPackageName(), 0);
            File srcFile = new File(ai.publicSourceDir);
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setType("application/vnd.android.package-archive");
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
            startActivity(Intent.createChooser(share, "PersianCoders"));
        } catch (Exception e) {
            Log.e("ShareApp", e.getMessage());
        }
*/

    }


    private void getDataFromWs() {

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
        c = dbcom.getSearch(cc.loadPrefString("emp_id"));
        salarylist = new ArrayList<search>();
        if (c.getCount() != 0 && c != null) {
            c.moveToFirst();
            do {
                search data = new search();

                data.setH_id(c.getString(0));
                data.setSal_year(c.getString(1));
                data.setSal_month(c.getString(2));
                data.setEmp_id(c.getString(3));
                data.setEmpTypeID(c.getString(4));
                data.setBasic_Salary_standard(c.getString(5));
                data.setBasic_Salary_Current(c.getString(6));
                data.setNet(c.getString(7));
                data.setAll_Allowances(c.getString(8));
                data.setAll_Deductions(c.getString(9));
                data.setUserid(c.getString(10));
                data.setDep_id(c.getString(11));
                data.setSec_id(c.getString(12));
                data.setDegree_id(c.getString(13));
                data.setStep_id(c.getString(14));
                data.setJob_id(c.getString(15));
                data.setEducation_id(c.getString(16));
                data.setNotes(c.getString(17));
                data.setA_Marrige(c.getString(18));
                data.setA_Kids(c.getString(19));
                data.setA_Education(c.getString(20));
                data.setA_EducationHigh(c.getString(21));
                data.setA_Profissional(c.getString(22));
                data.setA_Risk(c.getString(23));
                data.setA_Transportation(c.getString(24));
                data.setA_Engineering(c.getString(25));
                data.setA_position(c.getString(26));
                data.setA_Diffrences(c.getString(27));
                data.setD_tax(c.getString(28));
                data.setD_Retire(c.getString(29));
                data.setD_Loan(c.getString(30));
                data.setD_compernsations(c.getString(31));
                data.setD_insuracne(c.getString(32));
                data.setD_Social_solidarity(c.getString(33));
                data.setD_vacations(c.getString(34));
                data.setD_TakeOff(c.getString(35));
                data.setD_Properity(c.getString(36));
                data.setD_absent(c.getString(37));
                data.setD_laber(c.getString(38));
                data.setMobile_no(c.getString(39));
                data.setRisk_descr(c.getString(40));

                salarylist.add(data);

            } while (c.moveToNext());

//            int value=salarylist.size();
//            int reverseCount= 0;
//            if ((value % 3) == 1) {
//                reverseCount = reverseCount+ 2;
//            } else if ((value % 3) == 2) {
//                reverseCount = reverseCount + 1;
//            } else {
//                reverseCount= 0;
//            }

/*            for (int i = 0; i < 25; i++) {

                int reverseCount= 0;

                int value = i;
                int twoValue = value - 2;
                int oneValue = value - 1;

                if ((value % 3) == 0) {
                    reverseCount = 0;
                } else if ((twoValue % 3) == 0) {
                    reverseCount = 2;
                } else if ((oneValue % 3) == 0) {
                    reverseCount = 1;
                }

                Log.e("reverseCount for i",""+i+"= "+reverseCount+"");

            }*/

        }
        Log.e("arraylist_size", salarylist.size() + "");
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }


    }


    private void makeJsonLogout() {

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, URL.Url_logout,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("login", response);
                        jsonParseMatchList(response);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                cc.showSnackbar(relativeLayout, getString(R.string.ws_error));
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

        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");

    }

    private void jsonParseMatchList(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            pDialog.dismiss();
            Log.e("Logout", response + "");
            if (jsonObject.getString("status").equals("200")) {

                cc.showToast(jsonObject.getString("msg"));
                cc.logoutapp();

                Intent mIntent = new Intent(MainActivity.this,
                        LoginActivity.class);
                startActivity(mIntent);
                finish();

            } else {
                if (jsonObject.getString("status").equals("499") || jsonObject.getString("status").equals("498")) {
                    Intent mIntent = new Intent(MainActivity.this,
                            LoginActivity.class);
                    startActivity(mIntent);
                    finish();
                } else {
                    cc.showToast(jsonObject.getString("msg"));
                }
            }

        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

    /*

        StringBuffer sb;
        sb=new StringBuffer();
        sb.append("");
        sb.append("\n https://play.google.com/store/apps/details?id=com.nearbuy.tourguide&hl=en");
    imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                share.putExtra(Intent.EXTRA_TEXT, sb.toString());

                startActivity(Intent.createChooser(share, "Share via"));
            }
        });
*/
}
