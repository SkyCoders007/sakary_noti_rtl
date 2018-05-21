package com.mxi.android.salarynotification.adapter;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.activity.LeaveApplicationActivity;
import com.mxi.android.salarynotification.model.combo_salary_data;
import com.mxi.android.salarynotification.model.search;
import com.mxi.android.salarynotification.network.CommanClass;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by android on 16/11/16.
 */

public class PdfAdapter_2 extends RecyclerView.Adapter<PdfAdapter_2.MyViewHolder_2> {


    public static PdfDocument document;
    CommanClass cc;
    int i = 0;
    int reverseCounter = 0;
    ArrayList<combo_salary_data> data;
    private Context context;


    public PdfAdapter_2(Context context, ArrayList<combo_salary_data> data,int reverseCounter) {
        document = new PdfDocument();
        cc = new CommanClass(context);
        this.context = context;
        this.data = data;
        this.reverseCounter= reverseCounter;
    }


    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public MyViewHolder_2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item_list, parent, false);

        return new MyViewHolder_2(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder_2 holder, final int position) {
        final int number = position + 1;
        Log.e("number", number + "");
        i++;


        if (number == data.size()) {
            Log.e("InIf", "true");
            if (reverseCounter == 1) {
                Log.e("InIf", "true1");
                holder.ll_pdf_view_1.setVisibility(View.INVISIBLE);
                holder.ll_pdf_view_2.setVisibility(View.INVISIBLE);
            } else if (reverseCounter == 2) {
                Log.e("InIf", "true2");
                holder.ll_pdf_view_2.setVisibility(View.INVISIBLE);
            }

        }

        combo_salary_data comboSalaryData = data.get(position);

        search search_data = comboSalaryData.getSearch();


        holder.tv_month_pdf.setText(search_data.getSal_month());
        holder.tv_year_pdf.setText(search_data.getSal_year());
        holder.tv_pdf_net.setText(search_data.getNet());
        holder.pdf_deduct_property.setText(search_data.getD_Properity());
        holder.pdf_deduct_takeoff.setText(search_data.getD_TakeOff());
        holder.pdf_deduct_loan.setText(search_data.getD_Loan());
        holder.pdf_deduct_vacation.setText(search_data.getD_vacations());
        holder.pdf_deduct_all_allowance.setText(search_data.getAll_Allowances());
        holder.pdf_deduct_all_deduction.setText(search_data.getAll_Deductions());
        holder.pdf_deduct_retire.setText(search_data.getD_Retire());
        holder.pdf_deduct_tax.setText(search_data.getD_tax());
        int solidatory = convertInNumber(search_data.getD_insuracne()) + convertInNumber(search_data.getD_Social_solidarity());
        holder.pdf_deduct_insurance.setText(convertInString(solidatory));
        holder.pdf_deduct_labor.setText(search_data.getD_laber());
        holder.pdf_deduct_absent.setText(search_data.getD_absent());
        holder.pdf_allow_education.setText(search_data.getA_Education());
        holder.pdf_allow_transportation.setText(search_data.getA_Transportation());
        holder.pdf_allow_professional.setText(search_data.getA_Profissional());
        holder.pdf_allow_engineering.setText(search_data.getA_Engineering());
        holder.pdf_allow_position.setText(search_data.getA_position());
        holder.pdf_allow_risk.setText(search_data.getA_Risk());
        holder.pdf_basic_salary.setText(search_data.getBasic_Salary_standard());
        holder.pdf_current_salary.setText(search_data.getBasic_Salary_Current());
        int marital = convertInNumber(search_data.getA_Marrige()) + convertInNumber(search_data.getA_Kids());
        holder.pdf_marriage_kids.setText(convertInString(marital));
        int percent_allownace = (int) (convertInNumber(search_data.getAll_Allowances()) * (0.03));
        holder.pdf_net_three_percent.setText(convertInString(percent_allownace));


        search search_data_1 = null;
        search search_data_2 = null;

        if (number == data.size()) {
            Log.e("InIf", "true");
            if (reverseCounter == 1) {
                Log.e("InIf", "true1");
                holder.ll_pdf_view_1.setVisibility(View.INVISIBLE);
                holder.ll_pdf_view_2.setVisibility(View.INVISIBLE);
                search_data_1 = comboSalaryData.getSearch();
                search_data_2 = comboSalaryData.getSearch();
            } else if (reverseCounter == 2) {
                Log.e("InIf", "true2");
                holder.ll_pdf_view_2.setVisibility(View.INVISIBLE);
                search_data_1 = comboSalaryData.getSearch1();
                search_data_2 = comboSalaryData.getSearch1();
            }else{
                Log.e("@@@InElse", "true");
                search_data_1 = comboSalaryData.getSearch1();
                search_data_2 = comboSalaryData.getSearch2();
            }

        }else{
            search_data_1 = comboSalaryData.getSearch1();
            search_data_2 = comboSalaryData.getSearch2();
        }

//        search search_data_1 = comboSalaryData.getSearch1();
        holder.tv_month_pdf_1.setText(search_data_1.getSal_month());
        holder.tv_year_pdf_1.setText(search_data_1.getSal_year());
        holder.tv_pdf_net_1.setText(search_data_1.getNet());
        holder.pdf_deduct_property_1.setText(search_data_1.getD_Properity());
        holder.pdf_deduct_takeoff_1.setText(search_data_1.getD_TakeOff());
        holder.pdf_deduct_loan_1.setText(search_data_1.getD_Loan());
        holder.pdf_deduct_vacation_1.setText(search_data_1.getD_vacations());
        holder.pdf_deduct_all_allowance_1.setText(search_data_1.getAll_Allowances());
        holder.pdf_deduct_all_deduction_1.setText(search_data_1.getAll_Deductions());
        holder.pdf_deduct_retire_1.setText(search_data_1.getD_Retire());
        holder.pdf_deduct_tax_1.setText(search_data_1.getD_tax());
        int solidatory_1 = convertInNumber(search_data_1.getD_insuracne()) + convertInNumber(search_data_1.getD_Social_solidarity());
        holder.pdf_deduct_insurance_1.setText(convertInString(solidatory_1));
        holder.pdf_deduct_labor_1.setText(search_data_1.getD_laber());
        holder.pdf_deduct_absent_1.setText(search_data_1.getD_absent());
        holder.pdf_allow_education_1.setText(search_data_1.getA_Education());
        holder.pdf_allow_transportation_1.setText(search_data_1.getA_Transportation());
        holder.pdf_allow_professional_1.setText(search_data_1.getA_Profissional());
        holder.pdf_allow_engineering_1.setText(search_data_1.getA_Engineering());
        holder.pdf_allow_position_1.setText(search_data_1.getA_position());
        holder.pdf_allow_risk_1.setText(search_data_1.getA_Risk());
        holder.pdf_basic_salary_1.setText(search_data_1.getBasic_Salary_standard());
        holder.pdf_current_salary_1.setText(search_data_1.getBasic_Salary_Current());
        int marital_1 = convertInNumber(search_data_1.getA_Marrige()) + convertInNumber(search_data_1.getA_Kids());
        holder.pdf_marriage_kids_1.setText(convertInString(marital_1));
        int percent_allownace_1 = (int) (convertInNumber(search_data_1.getAll_Allowances()) * (0.03));
        holder.pdf_net_three_percent_1.setText(convertInString(percent_allownace_1));

//        search search_data_2 = comboSalaryData.getSearch2();
        holder.tv_month_pdf_2.setText(search_data_2.getSal_month());
        holder.tv_year_pdf_2.setText(search_data_2.getSal_year());
        holder.tv_pdf_net_2.setText(search_data_2.getNet());
        holder.pdf_deduct_property_2.setText(search_data_2.getD_Properity());
        holder.pdf_deduct_takeoff_2.setText(search_data.getD_TakeOff());
        holder.pdf_deduct_loan_2.setText(search_data_2.getD_Loan());
        holder.pdf_deduct_vacation_2.setText(search_data_2.getD_vacations());
        holder.pdf_deduct_all_allowance_2.setText(search_data_2.getAll_Allowances());
        holder.pdf_deduct_all_deduction_2.setText(search_data_2.getAll_Deductions());
        holder.pdf_deduct_retire_2.setText(search_data_2.getD_Retire());
        holder.pdf_deduct_tax_2.setText(search_data_2.getD_tax());
        int solidatory_2 = convertInNumber(search_data_2.getD_insuracne()) + convertInNumber(search_data_2.getD_Social_solidarity());
        holder.pdf_deduct_insurance_2.setText(convertInString(solidatory_2));
        holder.pdf_deduct_labor_2.setText(search_data_2.getD_laber());
        holder.pdf_deduct_absent_2.setText(search_data_2.getD_absent());
        holder.pdf_allow_education_2.setText(search_data_2.getA_Education());
        holder.pdf_allow_transportation_2.setText(search_data_2.getA_Transportation());
        holder.pdf_allow_professional_2.setText(search_data_2.getA_Profissional());
        holder.pdf_allow_engineering_2.setText(search_data_2.getA_Engineering());
        holder.pdf_allow_position_2.setText(search_data_2.getA_position());
        holder.pdf_allow_risk_2.setText(search_data_2.getA_Risk());
        holder.pdf_basic_salary_2.setText(search_data_2.getBasic_Salary_standard());
        holder.pdf_current_salary_2.setText(search_data_2.getBasic_Salary_Current());
        int marital_2 = convertInNumber(search_data_2.getA_Marrige()) + convertInNumber(search_data_2.getA_Kids());
        holder.pdf_marriage_kids_2.setText(convertInString(marital_2));
        int percent_allownace_2 = (int) (convertInNumber(search_data_2.getAll_Allowances()) * (0.03));
        holder.pdf_net_three_percent_2.setText(convertInString(percent_allownace_2));
/*


        if (position == (getItemCount() - 1) && PdfActivity.reverseCount != 0) {
            if (PdfActivity.reverseCount == 1) {
                holder.ll_pdf_view_1.setVisibility(View.INVISIBLE);
                holder.ll_pdf_view_2.setVisibility(View.INVISIBLE);

                search search_data_1 = comboSalaryData.getSearch();
                holder.tv_month_pdf_1.setText(search_data_1.getSal_month());
                holder.tv_year_pdf_1.setText(search_data_1.getSal_year());
                holder.tv_pdf_net_1.setText(search_data_1.getNet());
                holder.pdf_deduct_property_1.setText(search_data_1.getD_Properity());
                holder.pdf_deduct_takeoff_1.setText(search_data_1.getD_TakeOff());
                holder.pdf_deduct_loan_1.setText(search_data_1.getD_Loan());
                holder.pdf_deduct_vacation_1.setText(search_data_1.getD_vacations());
                holder.pdf_deduct_all_allowance_1.setText(search_data_1.getAll_Allowances());
                holder.pdf_deduct_all_deduction_1.setText(search_data_1.getAll_Deductions());
                holder.pdf_deduct_retire_1.setText(search_data_1.getD_Retire());
                holder.pdf_deduct_tax_1.setText(search_data_1.getD_tax());
                int solidatory_1 = convertInNumber(search_data_1.getD_insuracne()) + convertInNumber(search_data_1.getD_Social_solidarity());
                holder.pdf_deduct_insurance_1.setText(convertInString(solidatory_1));
                holder.pdf_deduct_labor_1.setText(search_data_1.getD_laber());
                holder.pdf_deduct_absent_1.setText(search_data_1.getD_absent());
                holder.pdf_allow_education_1.setText(search_data_1.getA_Education());
                holder.pdf_allow_transportation_1.setText(search_data_1.getA_Transportation());
                holder.pdf_allow_professional_1.setText(search_data_1.getA_Profissional());
                holder.pdf_allow_engineering_1.setText(search_data_1.getA_Engineering());
                holder.pdf_allow_position_1.setText(search_data_1.getA_position());
                holder.pdf_allow_risk_1.setText(search_data_1.getA_Risk());
                holder.pdf_basic_salary_1.setText(search_data_1.getBasic_Salary_standard());
                holder.pdf_current_salary_1.setText(search_data_1.getBasic_Salary_Current());
                int marital_1= convertInNumber(search_data_1.getA_Marrige())+convertInNumber(search_data_1.getA_Kids());
                holder.pdf_marriage_kids_1.setText(convertInString(marital_1));
                int percent_allownace_1= (int) (convertInNumber(search_data_1.getAll_Allowances())*(0.03));
                holder.pdf_net_three_percent_1.setText(convertInString(percent_allownace_1));

                search search_data_2 = comboSalaryData.getSearch();
                holder.tv_month_pdf_2.setText(search_data_2.getSal_month());
                holder.tv_year_pdf_2.setText(search_data_2.getSal_year());
                holder.tv_pdf_net_2.setText(search_data_2.getNet());
                holder.pdf_deduct_property_2.setText(search_data_2.getD_Properity());
                holder.pdf_deduct_takeoff_2.setText(search_data.getD_TakeOff());
                holder.pdf_deduct_loan_2.setText(search_data_2.getD_Loan());
                holder.pdf_deduct_vacation_2.setText(search_data_2.getD_vacations());
                holder.pdf_deduct_all_allowance_2.setText(search_data_2.getAll_Allowances());
                holder.pdf_deduct_all_deduction_2.setText(search_data_2.getAll_Deductions());
                holder.pdf_deduct_retire_2.setText(search_data_2.getD_Retire());
                holder.pdf_deduct_tax_2.setText(search_data_2.getD_tax());
                int solidatory_2 = convertInNumber(search_data_2.getD_insuracne()) + convertInNumber(search_data_2.getD_Social_solidarity());
                holder.pdf_deduct_insurance_2.setText(convertInString(solidatory_2));
                holder.pdf_deduct_labor_2.setText(search_data_2.getD_laber());
                holder.pdf_deduct_absent_2.setText(search_data_2.getD_absent());
                holder.pdf_allow_education_2.setText(search_data_2.getA_Education());
                holder.pdf_allow_transportation_2.setText(search_data_2.getA_Transportation());
                holder.pdf_allow_professional_2.setText(search_data_2.getA_Profissional());
                holder.pdf_allow_engineering_2.setText(search_data_2.getA_Engineering());
                holder.pdf_allow_position_2.setText(search_data_2.getA_position());
                holder.pdf_allow_risk_2.setText(search_data_2.getA_Risk());
                holder.pdf_basic_salary_2.setText(search_data_2.getBasic_Salary_standard());
                holder.pdf_current_salary_2.setText(search_data_2.getBasic_Salary_Current());
                int marital_2= convertInNumber(search_data_2.getA_Marrige())+convertInNumber(search_data_2.getA_Kids());
                holder.pdf_marriage_kids_2.setText(convertInString(marital_2));
                int percent_allownace_2= (int) (convertInNumber(search_data_2.getAll_Allowances())*(0.03));
                holder.pdf_net_three_percent_2.setText(convertInString(percent_allownace_2));

            } else if (PdfActivity.reverseCount == 2) {
                holder.ll_pdf_view_2.setVisibility(View.INVISIBLE);

                search search_data_2 = comboSalaryData.getSearch();
                holder.tv_month_pdf_2.setText(search_data_2.getSal_month());
                holder.tv_year_pdf_2.setText(search_data_2.getSal_year());
                holder.tv_pdf_net_2.setText(search_data_2.getNet());
                holder.pdf_deduct_property_2.setText(search_data_2.getD_Properity());
                holder.pdf_deduct_takeoff_2.setText(search_data.getD_TakeOff());
                holder.pdf_deduct_loan_2.setText(search_data_2.getD_Loan());
                holder.pdf_deduct_vacation_2.setText(search_data_2.getD_vacations());
                holder.pdf_deduct_all_allowance_2.setText(search_data_2.getAll_Allowances());
                holder.pdf_deduct_all_deduction_2.setText(search_data_2.getAll_Deductions());
                holder.pdf_deduct_retire_2.setText(search_data_2.getD_Retire());
                holder.pdf_deduct_tax_2.setText(search_data_2.getD_tax());
                int solidatory_2 = convertInNumber(search_data_2.getD_insuracne()) + convertInNumber(search_data_2.getD_Social_solidarity());
                holder.pdf_deduct_insurance_2.setText(convertInString(solidatory_2));
                holder.pdf_deduct_labor_2.setText(search_data_2.getD_laber());
                holder.pdf_deduct_absent_2.setText(search_data_2.getD_absent());
                holder.pdf_allow_education_2.setText(search_data_2.getA_Education());
                holder.pdf_allow_transportation_2.setText(search_data_2.getA_Transportation());
                holder.pdf_allow_professional_2.setText(search_data_2.getA_Profissional());
                holder.pdf_allow_engineering_2.setText(search_data_2.getA_Engineering());
                holder.pdf_allow_position_2.setText(search_data_2.getA_position());
                holder.pdf_allow_risk_2.setText(search_data_2.getA_Risk());
                holder.pdf_basic_salary_2.setText(search_data_2.getBasic_Salary_standard());
                holder.pdf_current_salary_2.setText(search_data_2.getBasic_Salary_Current());
                int marital_2= convertInNumber(search_data_2.getA_Marrige())+convertInNumber(search_data_2.getA_Kids());
                holder.pdf_marriage_kids_2.setText(convertInString(marital_2));
                int percent_allownace_2= (int) (convertInNumber(search_data_2.getAll_Allowances())*(0.03));
                holder.pdf_net_three_percent_2.setText(convertInString(percent_allownace_2));
            }
        } else {

            if(getItemCount()!=1){
                search search_data_1 = comboSalaryData.getSearch1();
                holder.tv_month_pdf_1.setText(search_data_1.getSal_month());
                holder.tv_year_pdf_1.setText(search_data_1.getSal_year());
                holder.tv_pdf_net_1.setText(search_data_1.getNet());
                holder.pdf_deduct_property_1.setText(search_data_1.getD_Properity());
                holder.pdf_deduct_takeoff_1.setText(search_data_1.getD_TakeOff());
                holder.pdf_deduct_loan_1.setText(search_data_1.getD_Loan());
                holder.pdf_deduct_vacation_1.setText(search_data_1.getD_vacations());
                holder.pdf_deduct_all_allowance_1.setText(search_data_1.getAll_Allowances());
                holder.pdf_deduct_all_deduction_1.setText(search_data_1.getAll_Deductions());
                holder.pdf_deduct_retire_1.setText(search_data_1.getD_Retire());
                holder.pdf_deduct_tax_1.setText(search_data_1.getD_tax());
                int solidatory_1 = convertInNumber(search_data_1.getD_insuracne()) + convertInNumber(search_data_1.getD_Social_solidarity());
                holder.pdf_deduct_insurance_1.setText(convertInString(solidatory_1));
                holder.pdf_deduct_labor_1.setText(search_data_1.getD_laber());
                holder.pdf_deduct_absent_1.setText(search_data_1.getD_absent());
                holder.pdf_allow_education_1.setText(search_data_1.getA_Education());
                holder.pdf_allow_transportation_1.setText(search_data_1.getA_Transportation());
                holder.pdf_allow_professional_1.setText(search_data_1.getA_Profissional());
                holder.pdf_allow_engineering_1.setText(search_data_1.getA_Engineering());
                holder.pdf_allow_position_1.setText(search_data_1.getA_position());
                holder.pdf_allow_risk_1.setText(search_data_1.getA_Risk());
                holder.pdf_basic_salary_1.setText(search_data_1.getBasic_Salary_standard());
                holder.pdf_current_salary_1.setText(search_data_1.getBasic_Salary_Current());
                int marital_1= convertInNumber(search_data_1.getA_Marrige())+convertInNumber(search_data_1.getA_Kids());
                holder.pdf_marriage_kids_1.setText(convertInString(marital_1));
                int percent_allownace_1= (int) (convertInNumber(search_data_1.getAll_Allowances())*(0.03));
                holder.pdf_net_three_percent_1.setText(convertInString(percent_allownace_1));


                search search_data_2 = comboSalaryData.getSearch2();
                holder.tv_month_pdf_2.setText(search_data_2.getSal_month());
                holder.tv_year_pdf_2.setText(search_data_2.getSal_year());
                holder.tv_pdf_net_2.setText(search_data_2.getNet());
                holder.pdf_deduct_property_2.setText(search_data_2.getD_Properity());
                holder.pdf_deduct_takeoff_2.setText(search_data.getD_TakeOff());
                holder.pdf_deduct_loan_2.setText(search_data_2.getD_Loan());
                holder.pdf_deduct_vacation_2.setText(search_data_2.getD_vacations());
                holder.pdf_deduct_all_allowance_2.setText(search_data_2.getAll_Allowances());
                holder.pdf_deduct_all_deduction_2.setText(search_data_2.getAll_Deductions());
                holder.pdf_deduct_retire_2.setText(search_data_2.getD_Retire());
                holder.pdf_deduct_tax_2.setText(search_data_2.getD_tax());
                int solidatory_2 = convertInNumber(search_data_2.getD_insuracne()) + convertInNumber(search_data_2.getD_Social_solidarity());
                holder.pdf_deduct_insurance_2.setText(convertInString(solidatory_2));
                holder.pdf_deduct_labor_2.setText(search_data_2.getD_laber());
                holder.pdf_deduct_absent_2.setText(search_data_2.getD_absent());
                holder.pdf_allow_education_2.setText(search_data_2.getA_Education());
                holder.pdf_allow_transportation_2.setText(search_data_2.getA_Transportation());
                holder.pdf_allow_professional_2.setText(search_data_2.getA_Profissional());
                holder.pdf_allow_engineering_2.setText(search_data_2.getA_Engineering());
                holder.pdf_allow_position_2.setText(search_data_2.getA_position());
                holder.pdf_allow_risk_2.setText(search_data_2.getA_Risk());
                holder.pdf_basic_salary_2.setText(search_data_2.getBasic_Salary_standard());
                holder.pdf_current_salary_2.setText(search_data_2.getBasic_Salary_Current());
                int marital_2= convertInNumber(search_data_2.getA_Marrige())+convertInNumber(search_data_2.getA_Kids());
                holder.pdf_marriage_kids_2.setText(convertInString(marital_2));
                int percent_allownace_2= (int) (convertInNumber(search_data_2.getAll_Allowances())*(0.03));
                holder.pdf_net_three_percent_2.setText(convertInString(percent_allownace_2));

            }else if(getItemCount()==1){

            }

        }*/

        holder.itemView.post(new Runnable() {
            @Override
            public void run() {

                View content = holder.itemView;
                Log.e("content", content + "");
                Log.e("content_height", content.getHeight() + "");
                Log.e("content_width", content.getWidth() + "");

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),
                        content.getHeight(), number).create();
                PdfDocument.Page page = document.startPage(pageInfo);
                content.draw(page.getCanvas());
                document.finishPage(page);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class MyViewHolder_2 extends RecyclerView.ViewHolder {

        LinearLayout ll_pdf_view, ll_pdf_view_1, ll_pdf_view_2;

        TextView tv_month_pdf, tv_year_pdf, tv_pdf_net, pdf_deduct_property, pdf_deduct_takeoff, pdf_deduct_loan;
        TextView pdf_deduct_vacation, pdf_deduct_all_allowance, pdf_deduct_all_deduction, pdf_deduct_retire, pdf_deduct_tax;
        TextView pdf_deduct_insurance, pdf_deduct_labor, pdf_deduct_absent, pdf_allow_education, pdf_allow_transportation;
        TextView pdf_allow_professional, pdf_allow_engineering, pdf_allow_position, pdf_allow_risk;
        TextView pdf_basic_salary, pdf_current_salary, pdf_marriage_kids, pdf_net_three_percent;

        TextView tv_month_pdf_1, tv_year_pdf_1, tv_pdf_net_1, pdf_deduct_property_1, pdf_deduct_takeoff_1, pdf_deduct_loan_1;
        TextView pdf_deduct_vacation_1, pdf_deduct_all_allowance_1, pdf_deduct_all_deduction_1, pdf_deduct_retire_1, pdf_deduct_tax_1;
        TextView pdf_deduct_insurance_1, pdf_deduct_labor_1, pdf_deduct_absent_1, pdf_allow_education_1, pdf_allow_transportation_1;
        TextView pdf_allow_professional_1, pdf_allow_engineering_1, pdf_allow_position_1, pdf_allow_risk_1;
        TextView pdf_basic_salary_1, pdf_current_salary_1, pdf_marriage_kids_1, pdf_net_three_percent_1;

        TextView tv_month_pdf_2, tv_year_pdf_2, tv_pdf_net_2, pdf_deduct_property_2, pdf_deduct_takeoff_2, pdf_deduct_loan_2;
        TextView pdf_deduct_vacation_2, pdf_deduct_all_allowance_2, pdf_deduct_all_deduction_2, pdf_deduct_retire_2, pdf_deduct_tax_2;
        TextView pdf_deduct_insurance_2, pdf_deduct_labor_2, pdf_deduct_absent_2, pdf_allow_education_2, pdf_allow_transportation_2;
        TextView pdf_allow_professional_2, pdf_allow_engineering_2, pdf_allow_position_2, pdf_allow_risk_2;
        TextView pdf_basic_salary_2, pdf_current_salary_2, pdf_marriage_kids_2, pdf_net_three_percent_2;

        public MyViewHolder_2(View itemView) {
            super(itemView);
            ll_pdf_view = (LinearLayout) itemView.findViewById(R.id.ll_pdf_view);
            ll_pdf_view_1 = (LinearLayout) itemView.findViewById(R.id.ll_pdf_view_1);
            ll_pdf_view_2 = (LinearLayout) itemView.findViewById(R.id.ll_pdf_view_2);

            tv_month_pdf = (TextView) itemView.findViewById(R.id.tv_month_pdf);
            tv_year_pdf = (TextView) itemView.findViewById(R.id.tv_year_pdf);
            tv_pdf_net = (TextView) itemView.findViewById(R.id.tv_pdf_net);
            pdf_deduct_property = (TextView) itemView.findViewById(R.id.pdf_deduct_property);
            pdf_deduct_takeoff = (TextView) itemView.findViewById(R.id.pdf_deduct_takeoff);
            pdf_deduct_loan = (TextView) itemView.findViewById(R.id.pdf_deduct_loan);
            pdf_deduct_vacation = (TextView) itemView.findViewById(R.id.pdf_deduct_vacation);
            pdf_deduct_all_allowance = (TextView) itemView.findViewById(R.id.pdf_deduct_all_allowance);
            pdf_deduct_all_deduction = (TextView) itemView.findViewById(R.id.pdf_deduct_all_deduction);
            pdf_deduct_retire = (TextView) itemView.findViewById(R.id.pdf_deduct_retire);
            pdf_deduct_tax = (TextView) itemView.findViewById(R.id.pdf_deduct_tax);
            pdf_deduct_insurance = (TextView) itemView.findViewById(R.id.pdf_deduct_insurance);
            pdf_deduct_labor = (TextView) itemView.findViewById(R.id.pdf_deduct_labor);
            pdf_deduct_absent = (TextView) itemView.findViewById(R.id.pdf_deduct_absent);
            pdf_allow_education = (TextView) itemView.findViewById(R.id.pdf_allow_education);
            pdf_allow_transportation = (TextView) itemView.findViewById(R.id.pdf_allow_transportation);
            pdf_allow_professional = (TextView) itemView.findViewById(R.id.pdf_allow_professional);
            pdf_allow_engineering = (TextView) itemView.findViewById(R.id.pdf_allow_engineering);
            pdf_allow_position = (TextView) itemView.findViewById(R.id.pdf_allow_position);
            pdf_allow_risk = (TextView) itemView.findViewById(R.id.pdf_allow_risk);
            pdf_basic_salary = (TextView) itemView.findViewById(R.id.pdf_basic_salary);
            pdf_current_salary = (TextView) itemView.findViewById(R.id.pdf_current_salary);
            pdf_marriage_kids = (TextView) itemView.findViewById(R.id.pdf_marriage_kids);
            pdf_net_three_percent = (TextView) itemView.findViewById(R.id.pdf_net_three_percent);


            tv_month_pdf_1 = (TextView) itemView.findViewById(R.id.tv_month_pdf_1);
            tv_year_pdf_1 = (TextView) itemView.findViewById(R.id.tv_year_pdf_1);
            tv_pdf_net_1 = (TextView) itemView.findViewById(R.id.tv_pdf_net_1);
            pdf_deduct_property_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_property_1);
            pdf_deduct_takeoff_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_takeoff_1);
            pdf_deduct_loan_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_loan_1);
            pdf_deduct_vacation_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_vacation_1);
            pdf_deduct_all_allowance_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_all_allowance_1);
            pdf_deduct_all_deduction_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_all_deduction_1);
            pdf_deduct_retire_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_retire_1);
            pdf_deduct_tax_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_tax_1);
            pdf_deduct_insurance_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_insurance_1);
            pdf_deduct_labor_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_labor_1);
            pdf_deduct_absent_1 = (TextView) itemView.findViewById(R.id.pdf_deduct_absent_1);
            pdf_allow_education_1 = (TextView) itemView.findViewById(R.id.pdf_allow_education_1);
            pdf_allow_transportation_1 = (TextView) itemView.findViewById(R.id.pdf_allow_transportation_1);
            pdf_allow_professional_1 = (TextView) itemView.findViewById(R.id.pdf_allow_professional_1);
            pdf_allow_engineering_1 = (TextView) itemView.findViewById(R.id.pdf_allow_engineering_1);
            pdf_allow_position_1 = (TextView) itemView.findViewById(R.id.pdf_allow_position_1);
            pdf_allow_risk_1 = (TextView) itemView.findViewById(R.id.pdf_allow_risk_1);
            pdf_basic_salary_1 = (TextView) itemView.findViewById(R.id.pdf_basic_salary_1);
            pdf_current_salary_1 = (TextView) itemView.findViewById(R.id.pdf_current_salary_1);
            pdf_marriage_kids_1 = (TextView) itemView.findViewById(R.id.pdf_marriage_kids_1);
            pdf_net_three_percent_1 = (TextView) itemView.findViewById(R.id.pdf_net_three_percent_1);


            tv_month_pdf_2 = (TextView) itemView.findViewById(R.id.tv_month_pdf_2);
            tv_year_pdf_2 = (TextView) itemView.findViewById(R.id.tv_year_pdf_2);
            tv_pdf_net_2 = (TextView) itemView.findViewById(R.id.tv_pdf_net_2);
            pdf_deduct_property_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_property_2);
            pdf_deduct_takeoff_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_takeoff_2);
            pdf_deduct_loan_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_loan_2);
            pdf_deduct_vacation_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_vacation_2);
            pdf_deduct_all_allowance_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_all_allowance_2);
            pdf_deduct_all_deduction_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_all_deduction_2);
            pdf_deduct_retire_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_retire_2);
            pdf_deduct_tax_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_tax_2);
            pdf_deduct_insurance_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_insurance_2);
            pdf_deduct_labor_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_labor_2);
            pdf_deduct_absent_2 = (TextView) itemView.findViewById(R.id.pdf_deduct_absent_2);
            pdf_allow_education_2 = (TextView) itemView.findViewById(R.id.pdf_allow_education_2);
            pdf_allow_transportation_2 = (TextView) itemView.findViewById(R.id.pdf_allow_transportation_2);
            pdf_allow_professional_2 = (TextView) itemView.findViewById(R.id.pdf_allow_professional_2);
            pdf_allow_engineering_2 = (TextView) itemView.findViewById(R.id.pdf_allow_engineering_2);
            pdf_allow_position_2 = (TextView) itemView.findViewById(R.id.pdf_allow_position_2);
            pdf_allow_risk_2 = (TextView) itemView.findViewById(R.id.pdf_allow_risk_2);
            pdf_basic_salary_2 = (TextView) itemView.findViewById(R.id.pdf_basic_salary_2);
            pdf_current_salary_2 = (TextView) itemView.findViewById(R.id.pdf_current_salary_2);
            pdf_marriage_kids_2 = (TextView) itemView.findViewById(R.id.pdf_marriage_kids_2);
            pdf_net_three_percent_2 = (TextView) itemView.findViewById(R.id.pdf_net_three_percent_2);

        }
    }

    public int convertInNumber(String str) {
        int sum = 0;

        try {
            NumberFormat ukFormat = NumberFormat.getNumberInstance(Locale.US);
            sum = ukFormat.parse(str).intValue();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("->->->->sum", sum + "");
        return sum;

    }


    public String convertInString(int str) {
        String yourFormattedString = NumberFormat.getNumberInstance(Locale.US).format(str);

        Log.e("->->->->FormattedString ", yourFormattedString);
        return yourFormattedString;
    }

}


