package com.mxi.android.salarynotification.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.model.search;
import com.mxi.android.salarynotification.network.CommanClass;

import java.util.ArrayList;

/**
 * Created by android on 16/11/16.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.MyViewHolder_2> {


    public static PdfDocument document = new PdfDocument();
    CommanClass cc;
    int i = 0;
    ArrayList<search> data ;
    private Context context;


    public PdfAdapter(Context context, ArrayList<search> data) {

        cc = new CommanClass(context);
        this.context = context;
        this.data = data;
    }


    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public MyViewHolder_2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_list_single_item, parent, false);

        return new MyViewHolder_2(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder_2 holder, final int position) {
        final int number = position + 1;
        i++;
        search search_data=data.get(position);

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
        holder.pdf_deduct_insurance.setText(search_data.getD_insuracne());
        holder.pdf_deduct_labor.setText(search_data.getD_laber());
        holder.pdf_deduct_absent.setText(search_data.getD_absent());
        holder.pdf_allow_education.setText(search_data.getA_Education());
        holder.pdf_allow_transportation.setText(search_data.getA_Transportation());
        holder.pdf_allow_professional.setText(search_data.getA_Profissional());
        holder.pdf_allow_engineering.setText(search_data.getA_Engineering());
        holder.pdf_allow_position.setText(search_data.getA_position());
        holder.pdf_allow_risk.setText(search_data.getA_Risk());
        holder.pdf_basic_salary.setText(search_data.getBasic_Salary_Current());
        holder.pdf_current_salary.setText(search_data.getD_compernsations());
        holder.pdf_marriage_kids.setText(search_data.getA_Marrige());

        if(i==3 || position==getItemCount()){
            i=0;
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

        TextView tv_month_pdf,tv_year_pdf,tv_pdf_net,pdf_deduct_property,pdf_deduct_takeoff,pdf_deduct_loan;
        TextView pdf_deduct_vacation,pdf_deduct_all_allowance,pdf_deduct_all_deduction,pdf_deduct_retire,pdf_deduct_tax;
        TextView pdf_deduct_insurance,pdf_deduct_labor,pdf_deduct_absent,pdf_allow_education,pdf_allow_transportation;
        TextView pdf_allow_professional,pdf_allow_engineering,pdf_allow_position,pdf_allow_risk;
        TextView pdf_basic_salary,pdf_current_salary,pdf_marriage_kids;

        public MyViewHolder_2(View itemView) {
            super(itemView);
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

        }
    }

}


