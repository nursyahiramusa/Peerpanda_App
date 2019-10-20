package com.example.peerpanda_app1.Model;

public class User {
    private String StuID;
    private String Pass;
    private String Name;
    private String Course;
    private String Sem;
    private String Gen;
    private String CPass;
    private String PhoneNo;

    public User() {
    }

    public User(String stuID, String pass, String name, String course, String sem, String CPass) {
        StuID = stuID;
        Pass = pass;
        Name = name;
        Course = course;
        Sem = sem;
        this.CPass = CPass;
    }



    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getSem() {
        return Sem;
    }

    public void setSem(String sem) {
        Sem = sem;
    }

    public String getGen() {
        return Gen;
    }

    public void setGen(String gen) {
        Gen = gen;
    }

    public String getCPass() {
        return CPass;
    }

    public void setCPass(String CPass) {
        this.CPass = CPass;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "User{}";
    }
}
