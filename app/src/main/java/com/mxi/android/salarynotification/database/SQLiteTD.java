package com.mxi.android.salarynotification.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

/**
 * Created by parth on 17/5/16.
 */
public class SQLiteTD {

    Context mcon;
    private static String dbname = "Salary.db";

    static String db_path = Environment.getExternalStorageDirectory()
            .toString() + "/" + dbname;

    SQLiteDatabase db;
    public static final String KEY_ROWID = "id";

    public SQLiteTD(Context con) {
        // TODO Auto-generated constructor stub
        mcon = con;

        db = mcon.openOrCreateDatabase(db_path, Context.MODE_PRIVATE, null);

        // Database Table for store all Languages list
        db.execSQL("CREATE TABLE IF NOT EXISTS Search(h_id VARCHAR PRIMARY KEY, "
                + " sal_year VARCHAR, sal_month VARCHAR,emp_id VARCHAR,empTypeID VARCHAR,Basic_Salary_standard VARCHAR," +
                "Basic_Salary_Current VARCHAR,Net VARCHAR,All_Allowances VARCHAR,All_Deductions VARCHAR,userid VARCHAR,dep_id VARCHAR," +
                "sec_id VARCHAR,degree_id VARCHAR,step_id VARCHAR,job_id VARCHAR,education_id VARCHAR,Notes VARCHAR,a_Marrige VARCHAR," +
                "a_Kids VARCHAR,a_Education VARCHAR,a_EducationHigh VARCHAR,a_Profissional VARCHAR,a_Risk VARCHAR,a_Transportation VARCHAR,a_Engineering VARCHAR," +
                "a_position VARCHAR,a_Diffrences VARCHAR,d_tax VARCHAR,d_Retire VARCHAR,d_Loan VARCHAR,d_compernsations VARCHAR,d_insuracne VARCHAR" +
                ",d_Social_solidarity VARCHAR,d_vacations,d_TakeOff VARCHAR,d_Properity VARCHAR,d_absent VARCHAR,d_laber VARCHAR,Mobile_no VARCHAR" +
                ",risk_descr VARCHAR); ");

        //========== REGISTER ==============================================
        db.execSQL("CREATE TABLE IF NOT EXISTS SignUp(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email VARCHAR, username VARCHAR, password VARCHAR, company_name VARCHAR, emp_id VARCHAR, emp_token VARCHAR); ");

    }

    public void inseartSearchData(String h_id, String sal_year, String sal_month,
                                  String emp_id, String empTypeID, String Basic_Salary_standard,
                                  String Basic_Salary_Current, String Net, String All_Allowances, String All_Deductions, String userid,
                                  String dep_id, String sec_id, String degree_id, String step_id, String job_id, String education_id,
                                  String Notes, String a_Marrige, String a_Kids, String a_Education, String a_EducationHigh,
                                  String a_Profissional, String a_Risk, String a_Transportation, String a_Engineering,
                                  String a_position, String a_Diffrences, String d_tax, String d_Retire, String d_Loan, String d_compernsations,
                                  String d_insuracne, String d_Social_solidarity, String d_vacations, String d_TakeOff, String d_Properity,
                                  String d_absent, String d_laber, String Mobile_no, String risk_descr) {


        String query = "INSERT INTO Search(h_id,sal_year,sal_month,emp_id,empTypeID,Basic_Salary_standard,Basic_Salary_Current," +
                "Net,All_Allowances,All_Deductions,userid,dep_id,sec_id,degree_id,step_id,job_id,education_id,Notes,a_Marrige," +
                "a_Kids,a_Education,a_EducationHigh,a_Profissional,a_Risk,a_Transportation,a_Engineering,a_position,a_Diffrences," +
                "d_tax,d_Retire,d_Loan,d_compernsations,d_insuracne,d_Social_solidarity,d_vacations,d_TakeOff,d_Properity," +
                "d_absent,d_laber,Mobile_no,risk_descr)VALUES ('"

                + h_id + "','"
                + sal_year + "','"
                + sal_month + "','"
                + emp_id + "','"
                + empTypeID + "','"
                + Basic_Salary_standard + "','"
                + Basic_Salary_Current + "','"
                + Net + "','"
                + All_Allowances + "','"
                + All_Deductions + "','"
                + userid + "','"
                + dep_id + "','"
                + sec_id + "','"
                + degree_id + "','"
                + step_id + "','"
                + job_id + "','"
                + education_id + "','"
                + Notes + "','"
                + a_Marrige + "','"
                + a_Kids + "','"
                + a_Education + "','"
                + a_EducationHigh + "','"
                + a_Profissional + "','"
                + a_Risk + "','"
                + a_Transportation + "','"
                + a_Engineering + "','"
                + a_position + "','"
                + a_Diffrences + "','"
                + d_tax + "','"
                + d_Retire + "','"
                + d_Loan + "','"
                + d_compernsations + "','"
                + d_insuracne + "','"
                + d_Social_solidarity + "','"
                + d_vacations + "','"
                + d_TakeOff + "','"
                + d_Properity + "','"
                + d_absent + "','"
                + d_laber + "','"
                + Mobile_no + "','"
                + risk_descr + "')";

        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("Error Search", e.getMessage());
        }
    }

    public Cursor getSearch(String emp_id) {
        Cursor cur = null;
        try {
            String query = "SELECT  * FROM  Search WHERE emp_id ='" + emp_id+"'";
            cur = db.rawQuery(query, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e("Error: getSearch ", e.getMessage());
        }

        return cur;
    }

    public void inseartORreplace(String h_id, String sal_year, String sal_month,
                                 String emp_id, String empTypeID, String Basic_Salary_standard,
                                 String Basic_Salary_Current, String Net, String All_Allowances, String All_Deductions, String userid,
                                 String dep_id, String sec_id, String degree_id, String step_id, String job_id, String education_id,
                                 String Notes, String a_Marrige, String a_Kids, String a_Education, String a_EducationHigh,
                                 String a_Profissional, String a_Risk, String a_Transportation, String a_Engineering,
                                 String a_position, String a_Diffrences, String d_tax, String d_Retire, String d_Loan, String d_compernsations,
                                 String d_insuracne, String d_Social_solidarity, String d_vacations, String d_TakeOff, String d_Properity,
                                 String d_absent, String d_laber, String Mobile_no, String risk_descr) {


        String query = "INSERT OR REPLACE INTO Search(h_id,sal_year,sal_month,emp_id,empTypeID,Basic_Salary_standard,Basic_Salary_Current," +
                "Net,All_Allowances,All_Deductions,userid,dep_id,sec_id,degree_id,step_id,job_id,education_id,Notes,a_Marrige," +
                "a_Kids,a_Education,a_EducationHigh,a_Profissional,a_Risk,a_Transportation,a_Engineering,a_position,a_Diffrences," +
                "d_tax,d_Retire,d_Loan,d_compernsations,d_insuracne,d_Social_solidarity,d_vacations,d_TakeOff,d_Properity," +
                "d_absent,d_laber,Mobile_no,risk_descr)VALUES ('"

                + h_id + "','"
                + sal_year + "','"
                + sal_month + "','"
                + emp_id + "','"
                + empTypeID + "','"
                + Basic_Salary_standard + "','"
                + Basic_Salary_Current + "','"
                + Net + "','"
                + All_Allowances + "','"
                + All_Deductions + "','"
                + userid + "','"
                + dep_id + "','"
                + sec_id + "','"
                + degree_id + "','"
                + step_id + "','"
                + job_id + "','"
                + education_id + "','"
                + Notes + "','"
                + a_Marrige + "','"
                + a_Kids + "','"
                + a_Education + "','"
                + a_EducationHigh + "','"
                + a_Profissional + "','"
                + a_Risk + "','"
                + a_Transportation + "','"
                + a_Engineering + "','"
                + a_position + "','"
                + a_Diffrences + "','"
                + d_tax + "','"
                + d_Retire + "','"
                + d_Loan + "','"
                + d_compernsations + "','"
                + d_insuracne + "','"
                + d_Social_solidarity + "','"
                + d_vacations + "','"
                + d_TakeOff + "','"
                + d_Properity + "','"
                + d_absent + "','"
                + d_laber + "','"
                + Mobile_no + "','"
                + risk_descr + "')";

        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("Error Search", e.getMessage());
        }
    }


/*    public Cursor SearchData(String year, String month) {
        Cursor cur = null;
        try {
            cur = db.rawQuery("SELECT * FROM " + "Search" + " WHERE "
                    + "sal_year" + " = '" + year + "' AND " + "sal_month" +
                    " = " + month, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cur;
    }*/


    public Cursor SearchData(String year, String month, String emp_id) {
        Cursor cur = null;
        try {
            cur = db.rawQuery("SELECT * FROM Search WHERE sal_year = '" + year + "' AND sal_month ='" + month+ "' AND emp_id = '" + emp_id+"'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cur;
    }

    public void deleteUserData(String emp_id) {
        try {
            db.execSQL("DELETE FROM Search WHERE emp_id = '" + emp_id + "'");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("deleteUserData",e.toString());
        }
    }

    public void deleteUser(String emp_id) {
        try {
            db.execSQL("DELETE FROM SignUp WHERE emp_id = '" + emp_id + "'");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("deleteUser",e.toString());
        }
    }

/*
    public Cursor getLastSalary() {

        Cursor cur = null;
        try {
            // SELECT * FROM Search ORDER BY sal_year AND sal_month DESC

            cur = db.rawQuery("SELECT * FROM Search ORDER BY sal_year AND sal_month DESC LIMIT 1", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cur;

    }*/

    public Cursor getLastSalary(String emp_id) {

        Cursor cur = null;
        try {
            // SELECT * FROM Search ORDER BY sal_year AND sal_month DESC

            cur = db.rawQuery("SELECT * FROM Search WHERE emp_id ='"+ emp_id +"' ORDER BY sal_year AND sal_month DESC LIMIT 1", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cur;

    }

    public void inseartSignup(String email, String username, String password, String company_name, String emp_id, String emp_token) {

        String query = "INSERT INTO SignUp(email,username,password,company_name,emp_id,emp_token)VALUES ('"

                + email + "','"
                + username + "','"
                + password + "','"
                + company_name + "','"
                + emp_id + "','"
                + emp_token + "')";

        try {
            db.execSQL(query);
        } catch (SQLException e) {
            Log.e("Error Signup", e.getMessage());
        }
    }

    public Cursor Login(String username, String password) {
        Cursor cur = null;
        try {
            cur = db.rawQuery("SELECT * FROM " + "SignUp" + " WHERE "
                    + "username" + " = '" + username + "' AND " + "password" +
                    " = '" + password + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cur;
    }

    public void deleteTable() {

        try {

            // db.execSQL("delete from Search");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            Log.e("Error : Delete table", e.getMessage());
        }

    }

}
