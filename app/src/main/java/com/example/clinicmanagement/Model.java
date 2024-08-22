package com.example.clinicmanagement;

public class Model {

    String id,address,email,phone,testMode,paymentMethod;

    public Model() {

    }

    public Model(String id,String address, String phone, String email) {
        this.id=id;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Model(String id, String address, String phone, String email, String testMode, String paymentMethod) {
        this.id=id;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.testMode=testMode;
        this.paymentMethod=paymentMethod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
