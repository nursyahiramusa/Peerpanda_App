package com.example.peerpanda_app1.Model;

public class Location {

    private String locID;
    private String loc_ccor;
    private String loc_name;

    public Location() {
    }

    public Location(String locID, String loc_ccor, String loc_name) {
        this.locID = locID;
        this.loc_ccor = loc_ccor;
        this.loc_name = loc_name;
    }

    public String getLocID() {
        return locID;
    }

    public void setLocID(String locID) {
        this.locID = locID;
    }

    public String getLoc_ccor() {
        return loc_ccor;
    }

    public void setLoc_ccor(String loc_ccor) {
        this.loc_ccor = loc_ccor;
    }

    public String getLoc_name() {
        return loc_name;
    }

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }
}
