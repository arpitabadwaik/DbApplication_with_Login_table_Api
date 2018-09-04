package com.example.admin1.dbapplication.db.tables;

import android.net.Uri;

import com.example.admin1.dbapplication.db.ContentDescriptor;

public class LoginTable {

    public static final String TABLE_NAME = "LoginTable";
    public static final String PATH = "LOGIN_TABLE";
    public static final int PATH_TOKEN = 1;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    public static class Cols{

        public static final String USER_ID = "user_id";
        public static final String USER_CITY = "user_city";
        public static final String USER_CONTACT_NO = "user_contact_no";
        public static final String USER_NAME = "user_name";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_ADDRESS = "user_address";
        public static final String EMP_TYPE = "emp_type";
        public static final String PROFILE_IMAGE = "profile_image";
        public static final String TOKEN= "token";
        public static final String EMP_ID= "emp_id";
    }
}
