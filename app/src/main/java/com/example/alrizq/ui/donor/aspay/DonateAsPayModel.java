package com.example.alrizq.ui.donor.aspay;

public class DonateAsPayModel {

    String id, donorId, ngoId, payment, phone, address, userName;

    public DonateAsPayModel() {
    }

    public DonateAsPayModel(String id, String donorId, String ngoId, String payment, String phone, String address, String userName) {
        this.id = id;
        this.donorId = donorId;
        this.ngoId = ngoId;
        this.payment = payment;
        this.phone = phone;
        this.address = address;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
