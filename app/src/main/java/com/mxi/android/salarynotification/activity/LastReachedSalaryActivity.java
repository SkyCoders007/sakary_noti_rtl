package com.mxi.android.salarynotification.activity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.database.SQLiteTD;
import com.mxi.android.salarynotification.fragments.AllowanceFragment;
import com.mxi.android.salarynotification.fragments.DeductionsFragment;
import com.mxi.android.salarynotification.fragments.SummaryFragment;
import com.mxi.android.salarynotification.model.last_salary;
import com.mxi.android.salarynotification.model.search;
import com.mxi.android.salarynotification.network.CommanClass;


import java.util.ArrayList;
import java.util.List;

public class LastReachedSalaryActivity extends AppCompatActivity {

    CommanClass cc;
    Toolbar toolbar;
    TabLayout tabLayout;
    TextView tv_header_name;
    ViewPager viewPager;
    SQLiteTD dbcom;
    Cursor c;
    public static ArrayList<last_salary> lastsalarylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_reached_salary);
        cc = new CommanClass(this);
        dbcom = new SQLiteTD(LastReachedSalaryActivity.this);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tv_header_name = (TextView) toolbar.findViewById(R.id.tv_header_name);
        if (SearchViewActivity.fromSearchView) {
            tv_header_name.setText(getResources().getString(R.string.search));
        } else {
            tv_header_name.setText(getResources().getString(R.string.salary));
        }

        setupViewPager(viewPager);
        tabLayout = new TabLayout(LastReachedSalaryActivity.this);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (!MainActivity.isSearch) {
            getLastSalary();
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SummaryFragment(), getResources().getString(R.string.summary));
        adapter.addFragment(new AllowanceFragment(), getResources().getString(R.string.allowance));
        adapter.addFragment(new DeductionsFragment(), getResources().getString(R.string.deduction));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getLastSalary() {

        try {
            c = dbcom.getLastSalary(cc.loadPrefString("emp_id"));
            lastsalarylist = new ArrayList<last_salary>();
            if (c.getCount() != 0 && c != null) {
                c.moveToFirst();
                do {

                    last_salary data = new last_salary();

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


                    lastsalarylist.add(data);

                } while (c.moveToNext());

            }
            Log.e("last_salary_list_size", lastsalarylist.size() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
