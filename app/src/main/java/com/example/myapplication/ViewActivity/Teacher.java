package com.example.myapplication.ViewActivity;

public class Teacher {
    private int teacherId;
    private String nameTeacher;
    private int experience;
    private String email;
    private String sex;

    public Teacher(int teacherId, String nameTeacher, int experience, String email, String sex) {
        this.teacherId = teacherId;
        this.nameTeacher = nameTeacher;
        this.experience = experience;
        this.email = email;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Teacher------------------------------------------"+
                "\n    Name       : " + nameTeacher + '\'' +
                "\n    Experience : " + experience + " years" +
                "\n    Email      : " + email + '\'' +
                "\n    Sex        : " + sex + '\'' +
                "\n";
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
