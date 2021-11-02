package com.sqliteexam.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sqliteexam.models.ManualCustomerModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE2";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_IS_ACTIVE = "IS_ACTIVE";
    public static final String COLUMN_ID = "ID";
    public static String DATABASE_TYPE = "";
    public static final String TYPE1 = "TYPE1";
    public static final String TYPE2 = "TYPE2";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="";
//        if (DATABASE_TYPE==TYPE1){
            query = "CREATE TABLE " + CUSTOMER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_IS_ACTIVE + " BOOL)";
//        } else if (DATABASE_TYPE==TYPE2){
//            query="";
//        }
        
        Log.e("TAG", query);
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(ManualCustomerModel manualCustomerModel, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        DATABASE_TYPE=type;
        long insert = 0;
      //  if (type.equals(TYPE1)){
            contentValues.put(COLUMN_CUSTOMER_NAME, manualCustomerModel.getCustomerName());
            contentValues.put(COLUMN_CUSTOMER_AGE, manualCustomerModel.getCustomerAge());
            contentValues.put(COLUMN_IS_ACTIVE, manualCustomerModel.isActive());
             insert = db.insert(CUSTOMER_TABLE, null, contentValues);

     //   }else if(type.equals(TYPE2)){
           // insert=0;
    //    }
        Log.e("TAG","insert value : "+ insert);
        return insert != -1;



        //return insert != -1;
    }

    public List<ManualCustomerModel> getEveryone() {
        List<ManualCustomerModel> list=new ArrayList<>();
        String query ="SELECT *  FROM "+CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor=db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                int customerId=cursor.getInt(0);
                String customerName=cursor.getString(1);
                int customerAge=cursor.getInt(2);
                boolean isActive= cursor.getInt(3) == 1;
                ManualCustomerModel customerModel=new ManualCustomerModel(customerId, customerName, customerAge, isActive);
                list.add(customerModel);

            }while (cursor.moveToNext());

        }else{
            //
        }
        return list;
    }
}
