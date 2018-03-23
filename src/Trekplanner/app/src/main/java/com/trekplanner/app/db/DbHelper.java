package com.trekplanner.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TBD on X.3.2018.
 */
public class DbHelper extends SQLiteOpenHelper {

    //Name of the database file
    private static final String DATABASE_NAME = "trekplanner.db";

    //Database version
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQL statements that creates items table
        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " ("
                + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemContract.ItemEntry.COLUMN_ITEM_TYPE + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_STATUS + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_WEIGHT + " REAL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_NOTES + " TEXT, "
                + ItemContract.ItemEntry.COLUMN_ITEM_PIC + " TEXT, "
                + ItemContract.ItemEntry.COLUMN_ITEM_DEFAULT + " INTEGER NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_ENERGY + " REAL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_PROTEIN + " REAL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_DEADLINE + " TEXT );";

        //Excute database statement
        sqLiteDatabase.execSQL(SQL_CREATE_ITEM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
