package com.example.appointment;

public class CreateAppointmentData {
    public CreateAppointmentData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CreateAppointmentData(String name, String email, String phone, String reason, String business, String status, String selectedDate, String selectedTime, String id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.reason = reason;
        this.business = business;
        this.selectedTime = selectedTime;
        this.selectedDate = selectedDate;
        this.status=status;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getBusiness() {
        return business;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    String name,email,phone,reason,business,selectedTime,selectedDate,status,id;
}
