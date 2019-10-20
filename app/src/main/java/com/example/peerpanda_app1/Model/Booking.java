package com.example.peerpanda_app1.Model;

import java.util.Date;

public class Booking {
    private  String phoneno;
    private String bookID;
    private String datetime;
    private String location;
    private String status;
    private String total_pay;
    private String coursecode;
    private String StuID;
    private String tutorID;

    public Booking() {
    }

    public Booking(String bookID, String datetime, String location, String status, String total_pay, String coursecode, String stuID, String tutorID) {
        this.bookID = bookID;
        this.datetime = datetime;
        this.location = location;
        this.status = status;
        this.total_pay = total_pay;
        this.coursecode = coursecode;
        StuID = stuID;
        this.tutorID = tutorID;
    }

    public Booking(String phoneno, String datetime, String location, String total_pay, String coursecode, String stuID, String tutorID) {
        this.phoneno = phoneno;
        this.datetime = datetime;
        this.location = location;
        this.total_pay = total_pay;
        this.coursecode = coursecode;
        StuID = stuID;
        this.tutorID = tutorID;
    }

    public Booking(String datetime, String location, String total_pay, String coursecode, String tutorID) {
        this.datetime = datetime;
        this.location = location;
        this.total_pay = total_pay;
        this.coursecode = coursecode;
        this.tutorID = tutorID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal_pay() {
        return total_pay;
    }

    public void setTotal_pay(String total_pay) {
        this.total_pay = total_pay;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public void setCoursecode(String coursecode) {
        this.coursecode = coursecode;
    }

    public String getStuID() {
        return StuID;
    }

    public void setStuID(String stuID) {
        StuID = stuID;
    }

    public String getTutorID() {
        return tutorID;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }
}
