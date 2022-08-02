package org.techtown.tab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class RDatabase {
    private static final String TAG = "RDatabase";
    private static org.techtown.tab.RDatabase database;
    public static String DATABASE_NAME = "R.db";
    public static String TABLE_R_INFO = "R_INFO";
    public static int DATABASE_VERSION = 3;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private RDatabase(Context context) {
        this.context = context;
    }

    public static org.techtown.tab.RDatabase getInstance(Context context) {
        if (database == null) {
            database = new org.techtown.tab.RDatabase(context);
        }

        return database;
    }

    public boolean open() {
        println("opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void close() {
        println("closing database [" + DATABASE_NAME + "].");
        db.close();
        database = null;
    }

    public Cursor rawQuery(String SQL) {
        println("\nexecuteQuery called.\n");

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            println("cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;
    }

    public boolean execSQL(String SQL) {
        println("\nexecute called.\n");

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }
    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase _db) {
            // TABLE_R_INFO
            println("creating table [" + TABLE_R_INFO + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_R_INFO;
            try {
                _db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_R_INFO + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  NAME TEXT, "
                    + "  REVIEW TEXT, "
                    + "  RATING REAL, "
                    + "  HID INTEGER, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

            // insert 2 book records


        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion < 2) {   // version 1

            }
        }

        private void insertRecord(SQLiteDatabase _db, String name, String review, float rating, int hid) {
            try {
                _db.execSQL( "insert into " + TABLE_R_INFO + "(NAME, REVIEW, RATING, HID) values ('" + name + "','" + review + "', '"  + rating + "', '"  + hid + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

    }

    public void insertRecord(String name, String review, float rating, int hid) {
        try {
            db.execSQL( "insert into " + TABLE_R_INFO + "(NAME, REVIEW, RATING, HID) values ('" + name + "', '" + review +  "', '" + rating + "', '"  + hid + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<RInfo> selectAll_r(int rid) {
        ArrayList<RInfo> result = new ArrayList<RInfo>();

        try {
            Cursor cursor = db.rawQuery("select NAME, REVIEW, RATING, HID from " + TABLE_R_INFO + " where HID = " + rid ,  null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                String review = cursor.getString(1);
                float rating = cursor.getFloat(2);
                int hid = cursor.getInt(3);


                RInfo info = new RInfo(name, review, rating, hid);
                result.add(info);
            }

        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }

        return result;
    }



    private void println(String msg) {
        Log.d(TAG, msg);
    }
}
