package com.example.myapplication.ViewActivity;

public class Session {
    private int sessionId;
    private String location;
    private String dayStart;
    private String dayEnd;

    public Session(int sessionId, String location, String dayStart, String dayEnd) {
        this.sessionId = sessionId;
        this.location = location;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
    }

    @Override
    public String toString() {
        return "Session---------------------------------------------------" + '\n' +
                "Location: " + location + '\n' +
                "Start Day: " + dayStart + '\n' +
                "End Day: " + dayEnd + '\n';
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }
}
