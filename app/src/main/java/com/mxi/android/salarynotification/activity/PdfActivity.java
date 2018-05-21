package com.mxi.android.salarynotification.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.adapter.PdfAdapter_2;
import com.mxi.android.salarynotification.model.combo_salary_data;
import com.mxi.android.salarynotification.model.search;
import com.mxi.android.salarynotification.network.CommanClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class PdfActivity extends AppCompatActivity implements View.OnClickListener {


    private String[] mFileList;
    private File mPath = new File(Environment.getExternalStorageDirectory() +"");
    private String mChosenFile="";
    private String pdfFilePath="";
    private static final String FTYPE = ".txt";
    private static final int DIALOG_LOAD_FILE = 1000;


    RecyclerView recyclerView;

    CommanClass cc;
    public static int reverseCount = 0;
    ProgressDialog progressDialog;
    TextView btn_pdf_back, btn_generate_pdf;
    public ArrayList<search> salaryInfoList;
    public ArrayList<combo_salary_data> comboInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        cc = new CommanClass(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_pdf);

        btn_generate_pdf = (TextView) findViewById(R.id.btn_generate_pdf);
        btn_pdf_back = (TextView) findViewById(R.id.btn_pdf_back);

        btn_generate_pdf.setOnClickListener(this);
        btn_pdf_back.setOnClickListener(this);


        salaryInfoList=new ArrayList<search>();

        salaryInfoList=MainActivity.salarylist;

        int value = salaryInfoList.size();
        int twoValue = value - 2;
        int oneValue = value - 1;

        if ((value % 3) == 0) {
            reverseCount = 0;
        } else if ((twoValue % 3) == 0) {
            reverseCount = 2;
        } else if ((oneValue % 3) == 0) {
            reverseCount = 1;
        }

        Log.e("ReverseCounter",reverseCount+"");

        comboInfoList= new ArrayList<combo_salary_data>();


        for (int i = 0; i < salaryInfoList.size(); i++) {
            combo_salary_data comboData = new combo_salary_data();

            comboData.setSearch(salaryInfoList.get(i));
            if (i + 1 < salaryInfoList.size()) {
                comboData.setSearch1(salaryInfoList.get(i + 1));
            } else {
                i = i + 1;
                comboInfoList.add(comboData);
                break;
            }
            if (i + 2 < salaryInfoList.size()) {
                comboData.setSearch2(salaryInfoList.get(i + 2));
            } else {
                i = i + 2;
                comboInfoList.add(comboData);
                break;
            }
            i = i + 2;
            comboInfoList.add(comboData);
        }

        Log.e("In PDF","comboInfoList "+comboInfoList.size());

        PdfAdapter_2 pdfAdapter = new PdfAdapter_2(PdfActivity.this, comboInfoList,reverseCount);
        LinearLayoutManager llm = new LinearLayoutManager(PdfActivity.this);
        recyclerView.setAdapter(pdfAdapter);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

    }

    private ArrayList<combo_salary_data> getComboListData() {

        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_generate_pdf:
                generatePDF();
//                loadFileList();
                break;
            case R.id.btn_pdf_back:
                finish();
                break;
        }
    }


    private void generatePDF() {
        CreatePdf createPdf = new CreatePdf();
        createPdf.execute();
    }

    public class CreatePdf extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(PdfActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                String pdfName = "Salary"+cc.currentDate()+".pdf";

                File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/SalaryNotification/AccountStatement/");

//                String path = pdfFilePath;


                if (!mediaStorageDir.exists()) {
                    mediaStorageDir.mkdirs();
                }

                File outputFile = new File(mediaStorageDir, pdfName);
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Error In", "Create New File");
                }


                PdfDocument document = PdfAdapter_2.document;
                FileOutputStream out = new FileOutputStream(outputFile);
                document.writeTo(out);
                out.close();

            } catch (IOException ioe) {
            } finally {

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            cc.showToast("PDF is generated");


            progressDialog.dismiss();

            String pdfName = "Salary"+cc.currentDate()+".pdf";

            String path = Environment.getExternalStorageDirectory() + "/SalaryNotification/AccountStatement/";
//            String path = pdfFilePath;

            File outputFile = new File(path, pdfName);

            Uri u_path = Uri.fromFile(outputFile );
            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfOpenintent.setDataAndType(u_path , "application/pdf");
            try {
                startActivity(pdfOpenintent);
                finish();
            }
            catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFileList() {
        try {
            mPath.mkdirs();
        }
        catch(SecurityException e) {
            Log.e("ChooseFolder", "unable to write on the sd card " + e.toString());
        }
        if(mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    return filename.contains(FTYPE) || sel.isDirectory();
                }

            };
            mFileList = mPath.list(filter);
            onCreateDialog(DIALOG_LOAD_FILE);
        }
        else {
            mFileList= new String[0];
        }
    }

    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(id) {
            case DIALOG_LOAD_FILE:
                builder.setTitle(mPath+"");
                if(mFileList == null) {
                    Log.e("ChooseFolder", "Showing file picker before loading the file list");
                    dialog = builder.create();
                    return dialog;
                }
                builder.setItems(mFileList, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mChosenFile = mFileList[which];
                        //you can do stuff with the file here too
                        Log.e("Chosen File",mChosenFile+"");
                        Log.e("Chosen File path",mPath+"/"+mChosenFile+"/");
                        pdfFilePath=mPath+"/"+mChosenFile+"/";
                        generatePDF();


                    }
                });
                break;
        }
        dialog = builder.show();
        return dialog;
    }
}
