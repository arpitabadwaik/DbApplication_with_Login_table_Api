package com.example.admin1.dbapplication.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.admin1.dbapplication.db.tables.LoginTable;

public class DatabaseProvider extends ContentProvider {

    private static final String UNKNOWN_URI = "Unknown URI";

    public static DatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        dbHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int token = ContentDescriptor.URI_MATCHER.match(uri);

        Cursor result = null;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doQuery(db, uri, LoginTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }
        }

        return result;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        Uri result = null;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doInsert(db, LoginTable.TABLE_NAME,
                        LoginTable.CONTENT_URI, uri, values);
                break;
            }
        }
        if (result == null) {
            throw new IllegalArgumentException(UNKNOWN_URI + uri);
        }

        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        int result = 0;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doDelete(db, uri, LoginTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
        }
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        int result = 0;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doUpdate(db, uri, LoginTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
        }
        return result;
    }

    private Cursor doQuery(SQLiteDatabase db, Uri uri, String tableName,
                           String[] projection, String selection, String[] selectionArgs,
                           String sortOrder)
    {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        Cursor result = builder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);

        result.setNotificationUri(getContext().getContentResolver(), uri);

        return result;
    }

    private int doUpdate(SQLiteDatabase db, Uri uri, String tableName,
                         String selection, String[] selectionArgs, ContentValues values)
    {
        int result = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    private int doDelete(SQLiteDatabase db, Uri uri, String tableName,
                         String selection, String[] selectionArgs)
    {
        int result = db.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }


    private Uri doInsert(SQLiteDatabase db, String tableName, Uri contentUri,
                         Uri uri, ContentValues values)
    {
        long id = db.insert(tableName, null, values);
        Uri result = contentUri.buildUpon().appendPath(String.valueOf(id))
                .build();
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

}
