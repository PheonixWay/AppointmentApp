package com.example.appointment;

public class profileUserDataModel {

    String bName,bOwner,bDetails,bEmail;

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbOwner() {
        return bOwner;
    }

    public void setbOwner(String bOwner) {
        this.bOwner = bOwner;
    }

    public String getbDetails() {
        return bDetails;
    }

    public void setbDetails(String bDetails) {
        this.bDetails = bDetails;
    }

    public String getbEmail() {
        return bEmail;
    }

    public void setbEmail(String bEmail) {
        this.bEmail = bEmail;
    }

    public profileUserDataModel() {
    }

    public profileUserDataModel(String bName, String bOwner, String bDetails, String bEmail) {
        this.bName = bName;
        this.bOwner = bOwner;
        this.bDetails = bDetails;
        this.bEmail = bEmail;
    }

}
