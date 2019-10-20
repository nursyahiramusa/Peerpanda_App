package com.example.peerpanda_app1.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.peerpanda_app1.Model.Booking;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="peerpandaDB.db";
    private static final int DB_VER=1;

    public Database(Context context){
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Booking> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={"bookID","datetime","location","status","StuID","total_pay","tutorID", "coursecode"};
        String sqlTable = "TutorDetail";

        qb.setTables(sqlTable);
        Cursor c =qb.query(db, sqlSelect, null, null, null, null, null,null);

        final List<Booking> result = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(new Booking(c.getString(c.getColumnIndex("bookID")),
                        c.getString(c.getColumnIndex("datetime")),
                        c.getString(c.getColumnIndex("location")),
                        c.getString(c.getColumnIndex("status")),
                        c.getString(c.getColumnIndex("StuID")),
                        c.getString(c.getColumnIndex("total_pay")),
                        c.getString(c.getColumnIndex("tutorID")),
                        c.getString(c.getColumnIndex("coursecode"))
                        ));
            }while(c.moveToNext());
        }
        return result;
    }

    public void addToCart(Booking booking){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO BookingDetail(datetime, location, total_pay, coursecode, tutorID) VALUES('%s', '%s', '%s', '%s', '%s');",
                booking.getDatetime(),
                booking.getLocation(),
                booking.getTotal_pay(),
                booking.getTutorID(),
                booking.getCoursecode());
        db.execSQL(query);
    }

    public void clearCart(){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM BookingDetail");
        db.execSQL(query);
    }
}
