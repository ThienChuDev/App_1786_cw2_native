package com.example.myapplication.ViewActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class yogaClassDAO {

    private SQLiteDatabase db;

    public yogaClassDAO(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean addClass(int sessionId, int teacherId, String dayOfWeek, String timeStart, String timeEnd, int capacity, int duration, int pricePerClass, String kind_of_classe) {
        ContentValues values = new ContentValues();
        values.put("sessionId", sessionId);
        values.put("teacherId", teacherId);
        values.put("day_of_week", dayOfWeek);
        values.put("timeStart", timeStart);
        values.put("timeEnd", timeEnd);
        values.put("capacity", capacity);
        values.put("duration", duration);
        values.put("price_per_class", pricePerClass);
        values.put("kind_of_classe", kind_of_classe);
        long row =  db.insert("Classes", null, values);
        return (row>0);
    }


    public ArrayList<yogaClasses> getAllClasses(Context context) {
        ArrayList<yogaClasses> cl = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Classes", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int classesId = cursor.getInt(0);
                int sessionId = cursor.getInt(1);
                int teacherID = cursor.getInt(2);
                String day_of_week = cursor.getString(3);
                String timeStart = cursor.getString(4);
                String timeEnd = cursor.getString(5);
                int capacity = cursor.getInt(6);
                int duration = cursor.getInt(7);
                int price_per_class = cursor.getInt(8);
                String kind_of_classe = cursor.getString(9);
                yogaClasses classes = new yogaClasses(classesId, sessionId, teacherID, day_of_week, timeStart, timeEnd, capacity, duration, price_per_class, kind_of_classe);
                cl.add(classes);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return cl;
    }


    public int updateClass(int classId, int sessionId, int teacherId, String dayOfWeek, String timeStart, String timeEnd, int capacity, int duration, int pricePerClass, String kind_of_classe) {
        ContentValues values = new ContentValues();
        values.put("sessionId", sessionId);
        values.put("teacherId", teacherId);
        values.put("day_of_week", dayOfWeek);
        values.put("timeStart", timeStart);
        values.put("timeEnd", timeEnd);
        values.put("capacity", capacity);
        values.put("duration", duration);
        values.put("price_per_class", pricePerClass);
        values.put("kind_of_classe", kind_of_classe);

        return db.update("Classes", values, "classeId = ?", new String[]{String.valueOf(classId)});
    }


    public int deleteClass(int classId) {
        return db.delete("Classes", "classeId = ?", new String[]{String.valueOf(classId)});
    }



}




