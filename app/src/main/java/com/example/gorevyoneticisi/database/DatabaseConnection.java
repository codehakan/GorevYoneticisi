package com.example.gorevyoneticisi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gorevyoneticisi.entity.tasks;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseConnection extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "plan";//database adı

    private static final String TABLE_NAME = "plan_listem";
    private static String PLAN_ID = "id";
    private static String PLAN_HEADER = "header";
    private static String PLAN_CONTENT = "content";
    private static String PLAN_TYPE = "type";
    private static String PLAN_DATE = "date";
    private static String PLAN_STATUS = "status";

    public DatabaseConnection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Databesi oluşturma kodu.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + PLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PLAN_HEADER + " TEXT,"
                + PLAN_CONTENT + " TEXT,"
                + PLAN_TYPE + " TEXT,"
                + PLAN_DATE + " TEXT,"
                + PLAN_STATUS + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE);
    }

    //id 'e göre veri silme işlemini yapan fonksiyon
    public void deletePlan(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, PLAN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // oluşturduğumuz database içerisindeki tabloya veri eklemek için ihtiyacımız olan fonksiyon.
    public void addPlan(tasks temp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAN_HEADER, temp.getTask_header());
        values.put(PLAN_CONTENT, temp.getTask_content());
        values.put(PLAN_TYPE, temp.getTask_type());
        values.put(PLAN_DATE, temp.getTask_date());
        values.put(PLAN_STATUS, temp.getTask_status());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // id'si belli olan veriyi çekmek için çalışması gereken fonksiyon
    public tasks detailPlan(int id) {
        tasks plan = new tasks();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            plan.setTask_header(cursor.getString(1));
            plan.setTask_content(cursor.getString(2));
            plan.setTask_type(cursor.getString(3));
            plan.setTask_date(cursor.getString(4));
            plan.setTask_status(cursor.getInt(5));
        }
        cursor.close();
        db.close();
        return plan;
    }

    // tabloda ki bütün verileri çeken fonksiyondur
    // 0 - tamamlanmayan planları çek
    // 1 - tamamlanan planları çek
    // 2 - her ikisini de çek(tüm veriler)
    public ArrayList<tasks> plans(int bool) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE status=" + bool;
        if (bool == 2)
            selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<tasks> planList = new ArrayList<tasks>();

        if (cursor.moveToFirst()) {
            do {
                tasks temp = new tasks();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    temp.setTask_id(cursor.getInt(cursor.getColumnIndex("id")));
                    temp.setTask_header(cursor.getString(cursor.getColumnIndex("header")));
                    temp.setTask_content(cursor.getString(cursor.getColumnIndex("content")));
                    temp.setTask_type(cursor.getString(cursor.getColumnIndex("type")));
                    temp.setTask_date(cursor.getString(cursor.getColumnIndex("date")));
                    temp.setTask_status(cursor.getInt(cursor.getColumnIndex("status")));
                }
                planList.add(temp);
            } while (cursor.moveToNext());
        }
        db.close();
        return planList;
    }

    // update yapan fonksiyon.
    public void editPlan(tasks temp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAN_HEADER, temp.getTask_header());
        values.put(PLAN_CONTENT, temp.getTask_content());
        values.put(PLAN_TYPE, temp.getTask_type());
        values.put(PLAN_DATE, temp.getTask_date());
        values.put(PLAN_STATUS, temp.getTask_status());

        db.update(TABLE_NAME, values, "id=" + temp.getTask_id(), null);

        /*db.update(TABLE_NAME, values, temp.getTask_id() + " = ?",
                new String[]{String.valueOf(temp.getTask_id())});*/
    }


    // tablodaki veri sayısını getirir
    public int getRowCount(String tur, int bool) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE type='" + tur + "' and status=" + bool;
        if (!(tur.equals("Günlük")) && !(tur.equals("Haftalık")) && !(tur.equals("Aylık")))
            countQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        return rowCount;
    }

    // tabloda ki tüm verileri siler
    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
