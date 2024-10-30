package com.example.myapplication.ViewActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class dbHelper extends SQLiteOpenHelper {
    public dbHelper(Context context) {
        super(context, "dbYoga", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlSessions = "CREATE TABLE Sessions (" +
                "sessionId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "location TEXT NOT NULL, " +
                "dayStart TEXT NOT NULL, " +
                "dayEnd TEXT NOT NULL)";
        db.execSQL(sqlSessions);


        String sqlTeachers = "CREATE TABLE Teachers (" +
                "teacherId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nameTeacher TEXT NOT NULL, " +
                "experience INTEGER, " +
                "email TEXT," +
                "sex TEXT)";

        db.execSQL(sqlTeachers);


        String sqlClasses = "CREATE TABLE Classes (" +
                "classeId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "sessionId INTEGER NOT NULL, " +
                "teacherId INTEGER NOT NULL, " +
                "day_of_week TEXT NOT NULL, " +
                "timeStart TEXT NOT NULL, " +
                "timeEnd TEXT NOT NULL, " +
                "capacity INTEGER NOT NULL, " +
                "duration INTEGER NOT NULL, " +
                "price_per_class INTEGER NOT NULL, " +
                "kind_of_classe TEXT, " +
                "FOREIGN KEY (sessionId) REFERENCES Sessions(sessionId), " +
                "FOREIGN KEY (teacherId) REFERENCES Teachers(teacherId))";
        db.execSQL(sqlClasses);


        db.execSQL("INSERT INTO Sessions (location, dayStart, dayEnd) VALUES ('New York', '2024-09-01', '2024-11-30')");
        db.execSQL("INSERT INTO Sessions (location, dayStart, dayEnd) VALUES ('Los Angeles', '2024-09-01', '2024-11-30')");
        db.execSQL("INSERT INTO Sessions (location, dayStart, dayEnd) VALUES ('San Francisco', '2024-09-01', '2024-11-30')");


        db.execSQL("INSERT INTO Teachers (nameTeacher, experience, email, sex) VALUES ('John Doe', 5, 'john@example.com', 'Male')");
        db.execSQL("INSERT INTO Teachers (nameTeacher, experience, email, sex) VALUES ('Jane Smith', 7, 'jane@example.com', 'Female')");
        db.execSQL("INSERT INTO Teachers (nameTeacher, experience, email, sex) VALUES ('Emily Davis', 3, 'emily@example.com', 'Female')");
        db.execSQL("INSERT INTO Teachers (nameTeacher, experience, email, sex) VALUES ('Michael Brown', 10, 'michael@example.com', 'Male')");


        db.execSQL("INSERT INTO Classes (sessionId, teacherId, day_of_week, timeStart, timeEnd, capacity, duration, price_per_class, kind_of_classe) " +
                "VALUES (1, 1, 'Monday', '10:00', '11:00', 20, 60, 15, 'Yoga for Beginners')");
        db.execSQL("INSERT INTO Classes (sessionId, teacherId, day_of_week, timeStart, timeEnd, capacity, duration, price_per_class, kind_of_classe) " +
                "VALUES (2, 2, 'Wednesday', '12:00', '13:30', 25, 90, 20, 'Advanced Yoga')");
        db.execSQL("INSERT INTO Classes (sessionId, teacherId, day_of_week, timeStart, timeEnd, capacity, duration, price_per_class, kind_of_classe) " +
                "VALUES (3, 3, 'Friday', '09:00', '10:30', 30, 90, 25, 'Family Yoga')");
        db.execSQL("INSERT INTO Classes (sessionId, teacherId, day_of_week, timeStart, timeEnd, capacity, duration, price_per_class, kind_of_classe) " +
                "VALUES (1, 4, 'Saturday', '08:00', '09:00', 15, 60, 10, 'Morning Yoga')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Classes");
        db.execSQL("DROP TABLE IF EXISTS Teachers");
        db.execSQL("DROP TABLE IF EXISTS Sessions");
        onCreate(db);
    }
}