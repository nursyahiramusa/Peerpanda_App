package com.example.peerpanda_app1.Model;

public class Rating {
    private String StuID;
    private String tutorID;
    private String ratingValue;
    private String comment;
    private String ratingID;

    public Rating() {
    }

    public Rating(String stuID, String tutorID, String ratingValue, String comment, String ratingID) {
        StuID = stuID;
        this.tutorID = tutorID;
        this.ratingValue = ratingValue;
        this.comment = comment;
        this.ratingID = ratingID;
    }

    public Rating(String stuID, String tutorID, String ratingValue, String comment) {
        StuID = stuID;
        this.tutorID = tutorID;
        this.ratingValue = ratingValue;
        this.comment = comment;
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

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRatingID() {
        return ratingID;
    }

    public void setRatingID(String ratingID) {
        this.ratingID = ratingID;
    }
}
