package com.example.admin1.dbapplication.db;

import android.content.UriMatcher;
import android.net.Uri;

import com.example.admin1.dbapplication.db.tables.LoginTable;

public class ContentDescriptor {

    public static final String AUTHORITY = "com.example.admin1.dbapplication";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, LoginTable.PATH, LoginTable.PATH_TOKEN);
        return matcher;
    }

}
