package com.example.peerpanda_app1.Model;

public class Tutor_Request {
    private String StuID;
    private String coursecode;
    private String coursegrade;
    private String name;
    private String semtaken;

    public Tutor_Request() {
    }

    public Tutor_Request(String stuID, String coursecode, String coursegrade, String name, String semtaken) {
        StuID = stuID;
        this.coursecode = coursecode;
        this.coursegrade = coursegrade;
        this.name = name;
        this.semtaken = semtaken;
    }

    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getCoursegrade() {
        return coursegrade;
    }

    public void setCoursegrade(String coursegrade) {
        this.coursegrade = coursegrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemtaken() {
        return semtaken;
    }

    public void setSemtaken(String semtaken) {
        this.semtaken = semtaken;
    }

    @Override
    public String toString() {
        return "Tutor_Request{}";
    }
}
