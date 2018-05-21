package com.mxi.android.salarynotification.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.activity.LastReachedSalaryActivity;
import com.mxi.android.salarynotification.activity.MainActivity;
import com.mxi.android.salarynotification.activity.SearchViewActivity;

/**
 * Created by android on 14/2/17.
 */

public class DeductionsFragment extends Fragment {

    TextView tv_tax_item_deduct, tv_loan_item_deduct, tv_insurance_item_deduct, tv_vacation_item_deduct;
    TextView tv_absents_item_deduct, tv_compensation_item_deduct, tv_property_item_deduct, tv_social_item_deduct;
    TextView tv_retire_item_deduct, tv_takeoff_item_deduct, tv_labor_item_deduct, tv_no_record;
    LinearLayout ll_view;

    public void DeductionsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_deduction, container, false);
        tv_tax_item_deduct = (TextView) itemView.findViewById(R.id.tv_tax_item_deduct);
        tv_loan_item_deduct = (TextView) itemView.findViewById(R.id.tv_loan_item_deduct);
        tv_insurance_item_deduct = (TextView) itemView.findViewById(R.id.tv_insurance_item_deduct);
        tv_vacation_item_deduct = (TextView) itemView.findViewById(R.id.tv_vacation_item_deduct);
        tv_absents_item_deduct = (TextView) itemView.findViewById(R.id.tv_absents_item_deduct);
        tv_retire_item_deduct = (TextView) itemView.findViewById(R.id.tv_retire_item_deduct);
        tv_compensation_item_deduct = (TextView) itemView.findViewById(R.id.tv_compensation_item_deduct);
        tv_property_item_deduct = (TextView) itemView.findViewById(R.id.tv_property_item_deduct);
        tv_social_item_deduct = (TextView) itemView.findViewById(R.id.tv_social_item_deduct);
        tv_takeoff_item_deduct = (TextView) itemView.findViewById(R.id.tv_takeoff_item_deduct);
        tv_labor_item_deduct = (TextView) itemView.findViewById(R.id.tv_labor_item_deduct);

        ll_view = (LinearLayout) itemView.findViewById(R.id.ll_view);
        tv_no_record = (TextView) itemView.findViewById(R.id.tv_no_record);

        try {
            if (!MainActivity.isSearch) {
                if (!LastReachedSalaryActivity.lastsalarylist.isEmpty()) {
                    ll_view.setVisibility(View.VISIBLE);
                    tv_no_record.setVisibility(View.GONE);
                    tv_tax_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_tax());
                    tv_loan_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_Loan());
                    tv_insurance_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_insuracne());
                    tv_vacation_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_vacations());
                    tv_absents_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_absent());
                    tv_retire_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_Retire());
                    tv_compensation_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_compernsations());
                    tv_social_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_Social_solidarity());
                    tv_property_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_Properity());
                    tv_takeoff_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_TakeOff());
                    tv_labor_item_deduct.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getD_laber());
                } else {
                    ll_view.setVisibility(View.GONE);
                    tv_no_record.setVisibility(View.VISIBLE);
                }
            } else {
                if (!SearchViewActivity.SearchRes.isEmpty()) {
                    ll_view.setVisibility(View.VISIBLE);
                    tv_no_record.setVisibility(View.GONE);
                    tv_tax_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_tax());
                    tv_loan_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_Loan());
                    tv_insurance_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_insuracne());
                    tv_vacation_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_vacations());
                    tv_absents_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_absent());
                    tv_retire_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_Retire());
                    tv_compensation_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_compernsations());
                    tv_social_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_Social_solidarity());
                    tv_property_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_Properity());
                    tv_takeoff_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_TakeOff());
                    tv_labor_item_deduct.setText(SearchViewActivity.SearchRes.get(0).getD_laber());
                } else {
                    ll_view.setVisibility(View.GONE);
                    tv_no_record.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return itemView;
    }
}
