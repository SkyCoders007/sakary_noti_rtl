package com.mxi.android.salarynotification.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mxi.android.salarynotification.R;
import com.mxi.android.salarynotification.adapter.PdfAdapter_2;
import com.mxi.android.salarynotification.database.SQLiteTD;
import com.mxi.android.salarynotification.model.combo_salary_data;
import com.mxi.android.salarynotification.model.search;
import com.mxi.android.salarynotification.network.AppController;
import com.mxi.android.salarynotification.network.CommanClass;
import com.mxi.android.salarynotification.network.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by android on 6/3/17.
 */
public class LeaveApplicationActivity extends Activity {
    private String[] mFileList;
    private File mPath = new File(Environment.getExternalStorageDirectory() + "");
    private String mChosenFile = "";
    private String pdfFilePath = "";
    private static final String FTYPE = ".txt";
    private static final int DIALOG_LOAD_FILE = 1000;


    RecyclerView recyclerView;

    CommanClass cc;
    public static int reverseCount = 0;
    ProgressDialog progressDialog;
    TextView btn_pdf_back, btn_generate_pdf;
    public ArrayList<search> salaryInfoList = MainActivity.salarylist;
    public ArrayList<combo_salary_data> comboInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        cc = new CommanClass(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_pdf);

        btn_generate_pdf = (TextView) findViewById(R.id.btn_generate_pdf);
        btn_pdf_back = (TextView) findViewById(R.id.btn_pdf_back);

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


        Log.e("ReverseCounter", reverseCount + "");

        comboInfoList = new ArrayList<combo_salary_data>();


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

        Log.e("In PDF", "comboInfoList " + comboInfoList.size());

        btn_generate_pdf.setVisibility(View.GONE);
        btn_pdf_back.setVisibility(View.GONE);

        PdfAdapter_2 pdfAdapter = new PdfAdapter_2(LeaveApplicationActivity.this, comboInfoList,reverseCount);
        LinearLayoutManager llm = new LinearLayoutManager(LeaveApplicationActivity.this);
        recyclerView.setAdapter(pdfAdapter);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(LeaveApplicationActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                generatePDF();
            }
        }, 2000);


    }




    private void generatePDF() {
        CreatePdf createPdf = new CreatePdf();
        createPdf.execute();
    }

    public class CreatePdf extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
/*            progressDialog = new ProgressDialog(LeaveApplicationActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(true);
            progressDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                String pdfName = "SalaryOnLeave.pdf";

                File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/SalaryNotification/Leave/");
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


//            progressDialog.dismiss();
            makeJsonLogout();

            /*String pdfName = "SalaryOnLeave.pdf";

            String path = Environment.getExternalStorageDirectory() + "/SalaryNotification/Leave/";
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
            }*/
        }
    }
/*

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



                    }
                });
                break;
        }
        dialog = builder.show();
        return dialog;
    }
*/


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
                progressDialog.dismiss();
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

        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");

    }

    private void jsonParseMatchList(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            progressDialog.dismiss();
            Log.e("Logout", response + "");
            if (jsonObject.getString("status").equals("200")) {

                SQLiteTD dbcon = new SQLiteTD(LeaveApplicationActivity.this);

                Log.e("LeaveApplication_success", response + "");
                dbcon.deleteUserData(cc.loadPrefString("emp_id"));
                dbcon.deleteUser(cc.loadPrefString("emp_id"));

                cc.showToast(jsonObject.getString("msg"));
                cc.logoutapp();

                Intent mIntent = new Intent(LeaveApplicationActivity.this,
                        LoginActivity.class);
                startActivity(mIntent);
                finish();

            } else {
                if (jsonObject.getString("status").equals("499") || jsonObject.getString("status").equals("498")) {
                    Log.e("LeaveApplication_fail", response + "");
                    Intent mIntent = new Intent(LeaveApplicationActivity.this,
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

}
