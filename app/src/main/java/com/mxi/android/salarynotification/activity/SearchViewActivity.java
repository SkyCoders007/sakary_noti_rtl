package com.mxi.android.salarynotification.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.database.SQLiteTD;
import com.mxi.android.salarynotification.model.search_view;
import com.mxi.android.salarynotification.network.CommanClass;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SearchViewActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    Toolbar toolbar;
    public static boolean fromSearchView = false;
    public static String year, month;
    CommanClass cc;
    TextView tv_header_name;
    LinearLayout ll_main;
    TextView tv_select_year_search_view, tv_select_month_search_view, tv_search_search_view;
    SQLiteTD dbcom;
    public static ArrayList<search_view> SearchRes;
    ProgressDialog pDialog;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        cc = new CommanClass(this);
        dbcom = new SQLiteTD(SearchViewActivity.this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv_header_name = (TextView) toolbar.findViewById(R.id.tv_header_name);
        tv_header_name.setText(getResources().getString(R.string.search));
        ll_main = (LinearLayout) findViewById(R.id.activity_search_view);
        tv_select_year_search_view = (TextView) findViewById(R.id.tv_select_year_search_view);
        tv_select_month_search_view = (TextView) findViewById(R.id.tv_select_month_search_view);
        tv_search_search_view = (TextView) findViewById(R.id.tv_search_search_view);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initListener();
    }

    private void initListener() {
        tv_select_year_search_view.setOnClickListener(this);
        tv_select_month_search_view.setOnClickListener(this);
        tv_search_search_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_year_search_view:
                showChangePasswordDialogs(true);
                break;
            case R.id.tv_select_month_search_view:
                showChangePasswordDialogs(false);
                break;
            case R.id.tv_search_search_view:
                if (isValue()) {
                    fromSearchView = true;
                    //  MainActivity.isSearch = true;
                    year = tv_select_year_search_view.getText().toString();
                    month = tv_select_month_search_view.getText().toString();
                    Log.e("search_year_month", year + "" + month);
                    // dbcom.SearchData(year, month);
                    search(year, month);
                    startActivity(new Intent(SearchViewActivity.this, LastReachedSalaryActivity.class));
                }

                break;
        }
    }

    private void search(String year, String month) {

        pDialog = new ProgressDialog(SearchViewActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.show();
       /* String yourFormattedString = null;
        try {
            yourFormattedString = NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(year));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*try {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            yourFormattedString = formatter.format(year);
            Log.e("yourFormattedString", yourFormattedString);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        try {
            c = dbcom.SearchData(year, month,cc.loadPrefString("emp_id"));
            SearchRes = new ArrayList<search_view>();
            if (c.getCount() != 0 && c != null) {
                c.moveToFirst();
                do {

                    search_view data = new search_view();

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

                    SearchRes.add(data);

                } while (c.moveToNext());

            }
            Log.e("arraylist_size", SearchRes.size() + "");
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private boolean isValue() {
        boolean value = false;
        if (tv_select_year_search_view.getText().toString().isEmpty()) {
            cc.showSnackbar(ll_main, getResources().getString(R.string.enter_year));
        } else if (tv_select_month_search_view.getText().toString().isEmpty()) {
            cc.showSnackbar(ll_main, getResources().getString(R.string.enter_month));
        } else {
            value = true;
        }

        return value;
    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Log.i("value is", "" + numberPicker);
    }

    private void showChangePasswordDialogs(final boolean year) {

        final Dialog dialog = new Dialog(SearchViewActivity.this);
        dialog.setContentView(R.layout.dialog);

        if (year) {
            dialog.setTitle("Year");
        } else {
            dialog.setTitle("Month");
        }

        dialog.setContentView(R.layout.dialog);
        Button b1 = (Button) dialog.findViewById(R.id.button1);
        final Button b2 = (Button) dialog.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.numberPicker1);
        if (year) {
            // np.setMaxValue(2100);
            np.setMaxValue(Calendar.getInstance().get(Calendar.YEAR));
            np.setValue(Calendar.getInstance().get(Calendar.YEAR));
            np.setMinValue(1800);
        } else {
            np.setMaxValue(12);
//            Log.e("Current Month",Calendar.getInstance().get(Calendar.MONTH)+"");
            np.setValue(Calendar.getInstance().get(Calendar.MONTH) + 1);
            np.setMinValue(1);
        }

        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year) {
                    tv_select_year_search_view.setText(String.valueOf(np.getValue()));
                } else {
                    tv_select_month_search_view.setText(String.valueOf(np.getValue()));
                }

                dialog.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
