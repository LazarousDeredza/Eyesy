package com.example.eyesyhopefyp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {
    public MyDbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables of the database (Initial)
        db.execSQL(Constants.CREATE_TABLE);


    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //upgrade database (if there is any structure change the db version)


        db.execSQL("DROP TABLE IF EXISTS " + Constants.Users);

        //Create again
        onCreate(db);


    }

    //clear databases
    public void Clear(Context context){

        SQLiteDatabase db =this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + Constants.Users);


        onCreate(db);
        db.close();
        Toast.makeText(context, "Cleared", Toast.LENGTH_SHORT).show();
    }

    //insert record to Company



  //INSERT INTO USERS
   public long insertToUsers(String Name,String Phone,String Email,String bName) {

        //get Writable database
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //id will auto generated by auto increment

        //insertData


        values.put(Constants.C_PHONE, Phone);
        values.put(Constants.NAME, Name);
        values.put(Constants.C_EMAIL, Email);
       values.put(Constants.BlindName, bName);


        //insert row , it will return record id of saved record
        long id = db.insert(Constants.Users, null, values);

        //close connection
        db.close();

        return id;
    }







    //GET ALL USERS
    public ArrayList<UserModel> getAllUSERS(){
        //order by query will allow to sort data e.g newest to oldest , first, last name ascending or descending
        //it will return list or records since we have used return type arrayList<Usermodel>

        ArrayList<UserModel> recordsList=new ArrayList<>();
        String selectQuery ="SELECT * FROM "+Constants.Users;
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") UserModel userModel=new UserModel(
                       ""+ cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.NAME)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.BlindName))

                );

                //add record to list
                recordsList.add(userModel);
            }while (cursor.moveToNext());

        }
        //close db connection
        db.close();


        //return the list
        return recordsList;

    }



}
