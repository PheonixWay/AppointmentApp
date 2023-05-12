package com.example.appointment;

public class CustomerProfileUserDataModel {

    String cName,cProfession,cAddress,cNumber;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcProfession() {
        return cProfession;
    }

    public void setcProfession(String cProfession) {
        this.cProfession = cProfession;
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String getcNumber() {
        return cNumber;
    }

    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }

    public CustomerProfileUserDataModel(String cName, String cProfession, String cAddress, String cNumber) {
        this.cName = cName;
        this.cProfession = cProfession;
        this.cAddress = cAddress;
        this.cNumber = cNumber;
    }

    public CustomerProfileUserDataModel() {
    }
}
