package com.app.kids.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.kids.item.ColorList;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database version
    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "drawing";

    //Color table name
    private static final String TABLE_NAME = "color";

    //Color table columns names
    private static final String KEY_ID = "id";
    private static final String KEY_COLOR_CODE = "color_code";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_BOOK_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_COLOR_CODE + " INTEGER"
                + ")";
        db.execSQL(CREATE_BOOK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Adding new color
    public void addColor(int color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COLOR_CODE, color);

        //Inserting row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    //Getting all color
    public List<ColorList> getColorDetail() {
        List<ColorList> scdLists = new ArrayList<>();
        //Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ColorList list = new ColorList();
                list.setColor(cursor.getInt(1));

                //Adding color list
                scdLists.add(list);
            } while (cursor.moveToNext());
        }

        //Return color list
        return scdLists;
    }

    //Check color or not
    public boolean isColorCode(int colorCode) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_COLOR_CODE + "=" + colorCode;
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount() == 0;
    }

}
