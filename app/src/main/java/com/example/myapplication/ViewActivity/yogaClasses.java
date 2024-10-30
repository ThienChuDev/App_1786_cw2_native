package com.example.myapplication.ViewActivity;

public class yogaClasses {

    private int classesId;
    private int sessionId;
    private int teacherID;
    private String day_of_week;
    private String timeStart ;
    private String timeEnd ;
    private int capacity ;
    private  int duration;
    private  int price_per_class ;
    private  String kind_of_classe;

    public yogaClasses(int classesId, int sessionId, int teacherID, String day_of_week, String timeStart, String timeEnd, int capacity, int duration, int price_per_class, String kind_of_classe) {
        this.classesId = classesId;
        this.sessionId = sessionId;
        this.teacherID = teacherID;
        this.day_of_week = day_of_week;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.capacity = capacity;
        this.duration = duration;
        this.price_per_class = price_per_class;
        this.kind_of_classe = kind_of_classe;
    }

    @Override
    public String toString() {
        return "Classe------------------------------------------------" + '\n'+
                "Day of Week: " + day_of_week + '\n' +
                "Time Start: " + timeStart + '\n' +
                "Time End: " + timeEnd + '\n' +
                "Capacity: " + capacity+" " +"USERS"+ '\n' +
                "Duration: " + duration+" " +"mininutes"+ '\n' +
                "Price per Class: " + price_per_class +"£"+ '\n' +
                "kind of classe: " + kind_of_classe;
    }


    public int getClassesId() {
        return classesId;
    }

    public void setClassesId(int classesId) {
        this.classesId = classesId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice_per_class() {
        return price_per_class;
    }

    public void setPrice_per_class(int price_per_class) {
        this.price_per_class = price_per_class;
    }

    public String getkind_of_classe() {
        return kind_of_classe;
    }

    public void setkind_of_classe(String description) {
        this.kind_of_classe = kind_of_classe;
    }
}
