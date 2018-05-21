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

public class AllowanceFragment extends Fragment {


    TextView tv_marriage_item_allowances, tv_engineering_item_allowances, tv_risk_item_allowances, tv_differences_item_allowances;
    TextView tv_education_item_allowances, tv_position_item_allowances, tv_transport_item_allowances, tv_kids_item_allowances;
    TextView tv_education_high_item_allowances, tv_professional_item_allowances, tv_no_record;

    LinearLayout ll_view;

    public void AllowanceFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_allowance, container, false);
        tv_marriage_item_allowances = (TextView) itemView.findViewById(R.id.tv_marriage_item_allowances);
        tv_engineering_item_allowances = (TextView) itemView.findViewById(R.id.tv_engineering_item_allowances);
        tv_risk_item_allowances = (TextView) itemView.findViewById(R.id.tv_risk_item_allowances);
        tv_differences_item_allowances = (TextView) itemView.findViewById(R.id.tv_differences_item_allowances);
        tv_education_item_allowances = (TextView) itemView.findViewById(R.id.tv_education_item_allowances);
        tv_education_high_item_allowances = (TextView) itemView.findViewById(R.id.tv_education_high_item_allowances);
        tv_position_item_allowances = (TextView) itemView.findViewById(R.id.tv_position_item_allowances);
        tv_transport_item_allowances = (TextView) itemView.findViewById(R.id.tv_transport_item_allowances);
        tv_kids_item_allowances = (TextView) itemView.findViewById(R.id.tv_kids_item_allowances);
        tv_professional_item_allowances = (TextView) itemView.findViewById(R.id.tv_professional_item_allowances);

        ll_view = (LinearLayout) itemView.findViewById(R.id.ll_view);
        tv_no_record = (TextView) itemView.findViewById(R.id.tv_no_record);

        try {
            if (!MainActivity.isSearch) {
                if (!LastReachedSalaryActivity.lastsalarylist.isEmpty()) {
                    ll_view.setVisibility(View.VISIBLE);
                    tv_no_record.setVisibility(View.GONE);
                    tv_kids_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Kids());
                    tv_marriage_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Marrige());
                    tv_engineering_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Engineering());
                    tv_risk_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Risk());
                    tv_differences_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Diffrences());
                    tv_education_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Education());
                    tv_education_high_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_EducationHigh());
                    tv_position_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_position());
                    tv_transport_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Transportation());
                    tv_professional_item_allowances.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getA_Profissional());
                } else {
                    ll_view.setVisibility(View.GONE);
                    tv_no_record.setVisibility(View.VISIBLE);
                }
            } else {
                if (!SearchViewActivity.SearchRes.isEmpty()) {
                    ll_view.setVisibility(View.VISIBLE);
                    tv_no_record.setVisibility(View.GONE);
                    tv_kids_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Kids());
                    tv_marriage_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Marrige());
                    tv_engineering_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Engineering());
                    tv_risk_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Risk());
                    tv_differences_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Diffrences());
                    tv_education_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Education());
                    tv_education_high_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_EducationHigh());
                    tv_position_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_position());
                    tv_transport_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Transportation());
                    tv_professional_item_allowances.setText(SearchViewActivity.SearchRes.get(0).getA_Profissional());
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
