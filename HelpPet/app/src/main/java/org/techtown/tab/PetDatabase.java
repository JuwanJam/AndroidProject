package org.techtown.tab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PetDatabase {
    private static final String TAG = "PetDatabase";
    private static org.techtown.tab.PetDatabase database;
    public static String DATABASE_NAME = "pet.db";
    public static String TABLE_PET_INFO = "PET_INFO";
    public static int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    private PetDatabase(Context context) {
        this.context = context;
    }

    public static org.techtown.tab.PetDatabase getInstance(Context context) {
        if (database == null) {
            database = new org.techtown.tab.PetDatabase(context);
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
            // TABLE_PET_INFO
            println("creating table [" + TABLE_PET_INFO + "].");

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_PET_INFO;
            try {
                _db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_PET_INFO + "("
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
           // insertRecord(_db, "코리안숏컷", "경기도 수원시 영통구", "010-8451-2632", "5개월 된 수컷 고양이, 발정기가 와서 중성화 수술이 필요합니다.");
           // insertRecord(_db, "시츄", "경기도 용인시 처인구", "010-2320-1088", "3살 암컷 강아지, 산책 도중 계단에서 굴러서 앞다리를 못 구부려요ㅜㅜ");

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
                _db.execSQL( "insert into " + TABLE_PET_INFO + "(NAME, LOCATION, MOBILE, CONDITION) values ('" + name + "','" + location + "', '" + mobile + "', '" + condition + "');" );
            } catch(Exception ex) {
                Log.e(TAG, "Exception in executing insert SQL.", ex);
            }
        }

    }

    public void insertRecord(String name, String location, String mobile, String condition) {
        try {
            db.execSQL( "insert into " + TABLE_PET_INFO + "(NAME, LOCATION, MOBILE, CONDITION) values ('" + name + "', '" + location + "', '" + mobile + "', '" + condition + "');" );
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executing insert SQL.", ex);
        }
    }

    public ArrayList<PetInfo> selectAll() {
        ArrayList<PetInfo> result = new ArrayList<PetInfo>();

        try {
            Cursor cursor = db.rawQuery("select NAME, LOCATION, MOBILE, CONDITION from " + TABLE_PET_INFO, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                String name = cursor.getString(0);
                String location = cursor.getString(1);
                String mobile = cursor.getString(2);
                String condition = cursor.getString(3);

                PetInfo info = new PetInfo(name, location, mobile, condition);
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
