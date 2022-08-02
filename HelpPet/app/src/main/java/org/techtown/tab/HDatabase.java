package org.techtown.tab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class HDatabase {
    private static final String TAG = "HDatabase";
    private static org.techtown.tab.HDatabase database;
    public static String DATABASE_NAME = "h.db";
    public static String TABLE_H_INFO = "H_INFO";
    public static int DATABASE_VERSION = 2;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private HDatabase(Context context) {
        this.context = context;
    }

    public static org.techtown.tab.HDatabase getInstance(Context context) {
        if (database == null) {
            database = new org.techtown.tab.HDatabase(context);
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
            // TABLE_H_INFO
            println("creating table [" + TABLE_H_INFO + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_H_INFO;
            try {
                _db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_H_INFO + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  NAME TEXT, "
                    + "  LOCATION TEXT, "
                    + "  MOBILE TEXT, "
                    + "  CONDITION TEXT, "
                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP "
                    + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

            // insert 2 book records
           // insertRecord(_db, "사랑동물병원", "경기도 수원시", "010-1000-1000", "굿");
            //insertRecord(_db, "행복동물병원", "경기도 평택시", "010-2000-1000", "쨩");

        }

        public void onOpen(SQLiteDatabase db) {
            println("opened database [" + DATABASE_NAME + "].");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");

            if (oldVersion < 2) {   // version 1

            }
        }

        private void insertRecord(SQLiteDatabase _db, String name, String location, String mobile, String condition) {
            try {
                _db.execSQL( "insert into " + TABLE_H_INFO + "(NAME, LOCATION, MOBILE, CONDITION) values ('" + name + "','" + location + "', '" + mobile + "', '" + condition + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

    }

    public void insertRecord(String name, String location, String mobile, String condition) {
        try {
            db.execSQL( "insert into " + TABLE_H_INFO + "(NAME, LOCATION, MOBILE, CONDITION) values ('" + name + "', '" + location + "', '" + mobile + "', '" + condition + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<HInfo> selectAll_h() {
        ArrayList<HInfo> result = new ArrayList<HInfo>();

        try {
            Cursor cursor = db.rawQuery("select _id, NAME, LOCATION, MOBILE, CONDITION from " + TABLE_H_INFO, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                int _id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String mobile = cursor.getString(3);
                String condition = cursor.getString(4);

                HInfo info = new HInfo(_id, name, location, mobile, condition);
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
