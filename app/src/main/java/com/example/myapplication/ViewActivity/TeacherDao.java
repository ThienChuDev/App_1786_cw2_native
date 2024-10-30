package com.example.myapplication.ViewActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TeacherDao {
    private SQLiteDatabase db;

    public TeacherDao(SQLiteDatabase db) {
        this.db = db;
    }


    public boolean addTeacher(String nameTeacher, int experience, String email, String sex) {
        ContentValues values = new ContentValues();
        values.put("nameTeacher", nameTeacher);
        values.put("experience", experience);
        values.put("email", email);
        values.put("sex", sex);

        long row = db.insert("Teachers", null, values);
        return (row > 0);
    }

    public ArrayList<Teacher> getAllTeachers(Context context) {
        ArrayList<Teacher> Teachers = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Teachers", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int teacherId = cursor.getInt(0);
                String nameTeacher = cursor.getString(1);
                int experience = cursor.getInt(2);
                String email = cursor.getString(3);
                String sex = cursor.getString(4);

                Teacher teacher = new Teacher(teacherId, nameTeacher, experience, email, sex);
                Teachers.add(teacher);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return Teachers;
    }


    public boolean updateTeacher(int teacherId, String nameTeacher, int experience, String email, String sex) {
        ContentValues values = new ContentValues();
        values.put("nameTeacher", nameTeacher);
        values.put("experience", experience);
        values.put("email", email);
        values.put("sex", sex);
        String selection = "teacherId = ?";
        String[] selectionArgs = { String.valueOf(teacherId) };
        int rowsUpdated = db.update("Teachers", values, selection, selectionArgs);
        return (rowsUpdated > 0);
    }

    public int deleteTeacher(int teacherId) {
        return db.delete("Teachers", "teacherId = ?", new String[]{String.valueOf(teacherId)});
    }
}
