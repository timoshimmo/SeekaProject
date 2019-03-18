package edu.freshfutures.seeka.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by tokmang on 8/4/2016.
 */
public class EduQualificationDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "Seeka.db";

    public static final String EDU_QUA_TABLE_NAME = "EducationQualifyEntity";
    public static final String CURR_CONVTR_TABLE_NAME = "CurrencyConverterEntity";

    public static final String EDU_QUA_ID = "_id";
    public static final String EDU_CTRY_CODE = "countryCode";
    public static final String EDU_COURSE_TYPE = "courseType";
    public static final String EDU_ELIGIBLE = "isEligible";
    public static final String EDU_QUA_MESSAGE = "message";
    public static final String EDU_QUA_CODE = "qualifyCode";

    public static final String CURR_COVTR_ID = "_id";
    public static final String CURR_DESTINATION_CODE = "destinationCode";
    public static final String CURR_RATE = "rate";
    public static final String CURR_SRC_CODE = "sourceCode";
    public static final String CURR_SRC_RATE = "sourceRate";
    public static final String CURR_UPDATED_DATE = "updatedDate";

    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String DOUBLE_TYPE = " DOUBLE";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_QUA_TABLE =
            "CREATE TABLE " + EDU_QUA_TABLE_NAME + " (" +
                    EDU_QUA_ID + " INTEGER PRIMARY KEY," +
                    EDU_CTRY_CODE + TEXT_TYPE + COMMA_SEP +
                    EDU_COURSE_TYPE + TEXT_TYPE + COMMA_SEP +
                    EDU_ELIGIBLE + BOOLEAN_TYPE + COMMA_SEP +
                    EDU_QUA_MESSAGE + TEXT_TYPE + COMMA_SEP +
                    EDU_QUA_CODE + INTEGER_TYPE + " )";

    private static final String SQL_CREATE_CURR_CONVERTER_TABLE =
            "CREATE TABLE " + CURR_CONVTR_TABLE_NAME + " (" +
                    CURR_COVTR_ID + " INTEGER PRIMARY KEY," +
                    CURR_DESTINATION_CODE + TEXT_TYPE + COMMA_SEP +
                    CURR_RATE + DOUBLE_TYPE + COMMA_SEP +
                    CURR_SRC_CODE + TEXT_TYPE + COMMA_SEP +
                    CURR_SRC_RATE + DOUBLE_TYPE + COMMA_SEP +
                    CURR_UPDATED_DATE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EDU_QUA_TABLE_NAME;

    private static final String SQL_DELETE_CURR =
            "DROP TABLE IF EXISTS " + CURR_CONVTR_TABLE_NAME;

    Context ctx;

    public EduQualificationDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_QUA_TABLE);
        db.execSQL(SQL_CREATE_CURR_CONVERTER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_CURR);
        onCreate(db);

    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertEduSystemData(String ctryCode, String cType, int isEligible, String message, int quaCode)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EDU_CTRY_CODE, ctryCode);
        contentValues.put(EDU_COURSE_TYPE, cType);
        contentValues.put(EDU_ELIGIBLE, isEligible);
        contentValues.put(EDU_QUA_MESSAGE, message);
        contentValues.put(EDU_QUA_CODE, quaCode);
        db.insert(EDU_QUA_TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean insertCurrencies(String destinationCode, double rate, String sourceCode, double sourceRate, String updtDate)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CURR_DESTINATION_CODE, destinationCode);
        contentValues.put(CURR_RATE, rate);
        contentValues.put(CURR_SRC_CODE, sourceCode);
        contentValues.put(CURR_SRC_RATE, sourceRate);
        contentValues.put(CURR_UPDATED_DATE, updtDate);
        db.insert(CURR_CONVTR_TABLE_NAME, null, contentValues);

        return true;
    }

    public Cursor getQuaCode(String ctry) throws SQLException {

        List<String> codeList = new ArrayList<String>();
        SQLiteDatabase dbase = this.getWritableDatabase();

        Cursor cursor = dbase.query(EDU_QUA_TABLE_NAME, new String[] {EDU_CTRY_CODE},
                EDU_CTRY_CODE +"=?", new String[] {ctry}, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public double getRatings(String ids) throws SQLException {

        double rating = 0;
        String Query = "SELECT " + CURR_RATE + " FROM " + CURR_CONVTR_TABLE_NAME + " WHERE " + CURR_DESTINATION_CODE + " = '" + ids + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        if(cursor != null) {
            if(cursor.moveToFirst()) {
                rating = cursor.getDouble(0);
            }
        }

        //Return the result
        return rating;
    }


    public int getEligibilityCount() {

        String Query = "SELECT " + EDU_CTRY_CODE + " FROM " + EDU_QUA_TABLE_NAME + " WHERE " + EDU_QUA_ID + "= 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        //Return the cursor count
        return cursor.getCount();

    }

    public int getCurrencyCount() {

        String Query = "SELECT " + CURR_DESTINATION_CODE + " FROM " + CURR_CONVTR_TABLE_NAME + " WHERE " + CURR_COVTR_ID + "= 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);

        //Return the cursor count
        return cursor.getCount();

    }

    public void deleteAllEligibility() {

        SQLiteDatabase dbase = this.getWritableDatabase();

        dbase.delete(EDU_QUA_TABLE_NAME, null, null);
        dbase.close();

    }

    public void deleteAllCurrencies() {

        SQLiteDatabase dbase = this.getWritableDatabase();

        dbase.delete(CURR_CONVTR_TABLE_NAME, null, null);
        dbase.close();

    }

    public ArrayList<HashMap<String, ArrayList<String>>> getAllEligibility(String ctryCode) {

        SQLiteDatabase dbase = this.getWritableDatabase();

        HashMap<String, ArrayList<String>> myData = new HashMap<>();
        ArrayList<HashMap<String, ArrayList<String>>> result = new ArrayList<>();
        ArrayList<String> myList = new ArrayList<>();

        Cursor cursor = dbase.query(EDU_QUA_TABLE_NAME, new String[]{EDU_ELIGIBLE, EDU_QUA_MESSAGE, EDU_COURSE_TYPE},
                EDU_CTRY_CODE + "=?", new String[]{ctryCode}, null, null, null);


        if(cursor.moveToFirst()) {
            do {


                myList.add(String.valueOf(cursor.getString(0)));
                myList.add(cursor.getString(1));
                myList.add(cursor.getString(2));

                myData.put(ctryCode, myList);

                result.add(myData);

            }
            while(cursor.moveToNext());
        }

        else {
            myList.add(null);
            myData.put(ctryCode, myList);
            result.add(myData);

            cursor.moveToNext();
        }


        cursor.close();
        return result;

    }

}
