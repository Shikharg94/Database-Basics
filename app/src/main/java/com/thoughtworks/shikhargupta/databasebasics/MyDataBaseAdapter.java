package com.thoughtworks.shikhargupta.databasebasics;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseAdapter{

    MyHelper myHelper;

    public MyDataBaseAdapter(Context context){
        myHelper = new MyHelper(context);
    }

    public long insertData(String name , String password){

        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myHelper.NAME , name);
        contentValues.put(myHelper.PASSWORD , password);
        long id = db.insert(MyHelper.TABLE_NAME , null , contentValues);

        return id;
    }

    static class MyHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "myDatabase.db";
        private static final String TABLE_NAME = "MYTABLE";
        private static final int DATABASE_VERSION = 5;
        private static final String UID = "_id";
        private static final String NAME  = "Name";
        private static final String PASSWORD  = "Password";
        private static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT , " + NAME+" VARCHAR(255) "+PASSWORD+" VARCHAR(255));";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public MyHelper(Context context) {
            super(context, DATABASE_NAME , null , DATABASE_VERSION);
            this.context = context;

            Message.message(context , "Constructor was called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //CREATE TABLE MYTABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT , Name VARCHAR(255));
            try {
                db.execSQL(CREATE_TABLE);
                Message.message(context , "Oncreate was called");
            } catch (SQLException e){
                Message.message(context , ""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL(DROP_TABLE);
                onCreate(db);
                Message.message(context , "OnUpgrade was called");
            } catch (SQLException e){
                Message.message(context , ""+e);
            }
        }
    }
}