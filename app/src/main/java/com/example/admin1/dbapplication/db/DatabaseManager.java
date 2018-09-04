package com.example.admin1.dbapplication.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;

import com.example.admin1.dbapplication.db.tables.LoginTable;
import com.example.admin1.dbapplication.models.LoginData;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseManager {

    public static int cnt;

    public static String getDbPath(Context context) {
        return context.getDatabasePath("dbapplication.db").getAbsolutePath();
    }

    public static void saveLoginDetails(Context dbActivity, LoginData loginData){
        saveUserInfo(dbActivity, loginData);
    }

    public static void saveUserInfo(Context dbActivity, LoginData loginData){
        if(loginData != null){
            ContentValues values = getContentValuesLoginTable(loginData);
            saveValues(dbActivity, LoginTable.CONTENT_URI, values, null);
        }

    }

    // Insert values in to database

    public static ContentValues getContentValuesLoginTable(LoginData loginData){
        ContentValues values = new ContentValues();
        try {
            values.put(LoginTable.Cols.USER_ID, loginData.user_id != null ? loginData.user_id : "");
            values.put(LoginTable.Cols.USER_CITY, loginData.city != null ? loginData.city : "");
            values.put(LoginTable.Cols.USER_CONTACT_NO, loginData.contact_no != null ? loginData.contact_no : "");
            values.put(LoginTable.Cols.USER_NAME, loginData.user_name != null ? loginData.user_name : "");
            values.put(LoginTable.Cols.USER_EMAIL, loginData.email_id != null ? loginData.emp_id : "");
            values.put(LoginTable.Cols.USER_ADDRESS, loginData.address != null ? loginData.address : "");
            values.put(LoginTable.Cols.EMP_TYPE, loginData.emp_type != null ? loginData.emp_type : "");
            values.put(LoginTable.Cols.PROFILE_IMAGE, loginData.profile_image != null ? loginData.profile_image : "");
            values.put(LoginTable.Cols.TOKEN, loginData.token != null ? loginData.token : "");
            values.put(LoginTable.Cols.EMP_ID, loginData.emp_id != null ? loginData.emp_id : "");

        }
       catch (Exception e){
           e.printStackTrace();
        }
        return values;
    }

    public static void saveValues(Context dbActivity, Uri table, ContentValues values, String condition){

        ContentResolver resolver = dbActivity.getContentResolver();
        Cursor cursor = resolver.query(table, null, condition, null, null);
        if (cursor != null && cursor.getCount() > 0){
            resolver.update(table, values, condition, null);
        }
        else {
            resolver.insert(table, values);
        }
        if (cursor != null){
            cursor.close();
        }
    }






    // For selecting stored values from database

    public static LoginData getData(String userId){
        SQLiteDatabase sqLiteDatabase = DatabaseProvider.dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + LoginTable.TABLE_NAME, null);
        LoginData cards = getDataListFromCursor(cursor);
        if(cursor != null){
            cnt = cursor.getCount();
            cursor.close();
        }
        if(sqLiteDatabase.isOpen()){
            sqLiteDatabase.close();
        }
        return cards;
    }

    public static LoginData getDataListFromCursor(Cursor cursor){
        LoginData cards = null;
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                cards = getDataFromCursor(cursor);
                cursor.moveToNext();
            }

            if (cursor != null)
                cursor.close();
        }
       return cards;
    }

    public static  LoginData getDataFromCursor(Cursor cursor){
        LoginData newCards = new LoginData();
        newCards.user_id = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_ID))!= null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_ID)) : "";
        newCards.city = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_CITY)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_CITY)) : "";
        newCards.contact_no = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_CONTACT_NO)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_CONTACT_NO)) : "";
        newCards.user_name = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_NAME)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_NAME)) : "";
        newCards.email_id = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_EMAIL)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_EMAIL)) : "";
        newCards.emp_type = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.EMP_TYPE)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols. EMP_TYPE)) : "";
        newCards.address = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_ADDRESS)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_ADDRESS)) : "";
        newCards.profile_image = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.PROFILE_IMAGE)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.PROFILE_IMAGE)) : "";
        newCards.token = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.TOKEN)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.TOKEN)) : "";
        newCards.emp_id = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.EMP_ID)) != null ? cursor.getString(cursor.getColumnIndex(LoginTable.Cols.EMP_ID)) : "";
        return newCards;
    }




    // Update data into database

    public static void updateData(String userId, String status){
        SQLiteDatabase sqLiteDatabase = DatabaseProvider.dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(LoginTable.Cols.PROFILE_IMAGE, status);
        String[] args= new String[]{userId};
        sqLiteDatabase.update(LoginTable.TABLE_NAME, values, "emp_id=?", args);
        if(sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }




    //Delete data from database

    public static void deleteData(Context context, String userId){
        try {
            String condition = LoginTable.Cols.EMP_ID + "='" + userId + "'";
            context.getContentResolver().delete(LoginTable.CONTENT_URI, condition, null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
