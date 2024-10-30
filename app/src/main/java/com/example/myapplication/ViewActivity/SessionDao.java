package com.example.myapplication.ViewActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class SessionDao {
    private SQLiteDatabase db;

    public SessionDao(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean addSession(String location, String dayStart, String dayEnd) {
        ContentValues values = new ContentValues();
        values.put("location", location);
        values.put("dayStart", dayStart);
        values.put("dayEnd", dayEnd);
        long row = db.insert("Sessions", null, values);
        return (row > 0);
    }

    public ArrayList<Session> getAllSessions (Context context) {
        ArrayList<Session> sessionsList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Sessions", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int sessionId = cursor.getInt(0);
                String location = cursor.getString(1);
                String dayStart = cursor.getString(2);
                String dayEnd = cursor.getString(3);

                Session session = new Session(sessionId, location, dayStart, dayEnd);
                sessionsList.add(session);
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d("Session Info", sessionsList.toString());
        return sessionsList;
    }

    public boolean updateSession(int sessionId, String location, String dayStart, String dayEnd) {
        ContentValues values = new ContentValues();
        values.put("location", location);
        values.put("dayStart", dayStart);
        values.put("dayEnd", dayEnd);
        String selection = "sessionId = ?";
        String[] selectionArgs = { String.valueOf(sessionId) };
        int rowsUpdated = db.update("Sessions", values, selection, selectionArgs);
        return (rowsUpdated > 0);
    }

    public int deleteSession(int sessionId) {

         return db.delete("Sessions", "sessionId = ?", new String[]{String.valueOf(sessionId)});

    }
}
