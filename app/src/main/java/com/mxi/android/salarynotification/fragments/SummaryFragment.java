package com.mxi.android.salarynotification.fragments;

import android.database.Cursor;
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
import com.mxi.android.salarynotification.database.SQLiteTD;

/**
 * Created by android on 14/2/17.
 */

public class SummaryFragment extends Fragment {

    TextView tv_degree_item_summary, tv_basic_salary_item_summary, tv_year_item_summary;
    TextView tv_net_item_summary, tv_allowance_item_summary, tv_deduction_item_summary, tv_no_record;

    LinearLayout ll_view;
    SQLiteTD dbcom;
    Cursor c;

    public void SummaryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_summary, container, false);
        dbcom = new SQLiteTD(getActivity());
        tv_degree_item_summary = (TextView) itemView.findViewById(R.id.tv_degree_item_summary);
        tv_basic_salary_item_summary = (TextView) itemView.findViewById(R.id.tv_basic_salary_item_summary);
        tv_year_item_summary = (TextView) itemView.findViewById(R.id.tv_year_item_summary);
        tv_net_item_summary = (TextView) itemView.findViewById(R.id.tv_net_item_summary);
        tv_allowance_item_summary = (TextView) itemView.findViewById(R.id.tv_allowance_item_summary);
        tv_deduction_item_summary = (TextView) itemView.findViewById(R.id.tv_deduction_item_summary);

        ll_view = (LinearLayout) itemView.findViewById(R.id.ll_view);
        tv_no_record = (TextView) itemView.findViewById(R.id.tv_no_record);


        try {
            if (!MainActivity.isSearch) {
                if (!LastReachedSalaryActivity.lastsalarylist.isEmpty()) {
                    ll_view.setVisibility(View.VISIBLE);
                    tv_no_record.setVisibility(View.GONE);
                    tv_degree_item_summary.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getDegree_id() + "/" + LastReachedSalaryActivity.lastsalarylist.get(0).getStep_id());
                    tv_basic_salary_item_summary.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getBasic_Salary_Current());
                    tv_year_item_summary.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getSal_year() + " - " + LastReachedSalaryActivity.lastsalarylist.get(0).getSal_month());
                    tv_net_item_summary.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getNet());
                    tv_allowance_item_summary.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getAll_Allowances());
                    tv_deduction_item_summary.setText(LastReachedSalaryActivity.lastsalarylist.get(0).getAll_Deductions());
                } else {
                    ll_view.setVisibility(View.GONE);
                    tv_no_record.setVisibility(View.VISIBLE);
                }
            } else {
                if (!SearchViewActivity.SearchRes.isEmpty()) {

                    ll_view.setVisibility(View.VISIBLE);
                    tv_no_record.setVisibility(View.GONE);
                    tv_degree_item_summary.setText(SearchViewActivity.SearchRes.get(0).getDegree_id() + "/" + SearchViewActivity.SearchRes.get(0).getStep_id());
                    tv_basic_salary_item_summary.setText(SearchViewActivity.SearchRes.get(0).getBasic_Salary_Current());
                    tv_year_item_summary.setText(SearchViewActivity.SearchRes.get(0).getSal_year() + "/" + SearchViewActivity.SearchRes.get(0).getSal_month());
                    tv_net_item_summary.setText(SearchViewActivity.SearchRes.get(0).getNet());
                    tv_allowance_item_summary.setText(SearchViewActivity.SearchRes.get(0).getAll_Allowances());
                    tv_deduction_item_summary.setText(SearchViewActivity.SearchRes.get(0).getAll_Deductions());
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
