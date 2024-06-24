package com.example.alrizq.donation;

public class donationPojo {

    String address,id,itemName,quantity,status, timeOfPreparation,uId, createdAt;

    public donationPojo() {
    }

    public donationPojo(String address, String id, String itemName, String quantity, String status, String timeOfPrepration, String uId, String createdAt) {
        this.address = address;
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
        this.timeOfPreparation = timeOfPrepration;
        this.uId = uId;
        this.createdAt = createdAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeOfPreparation() {
        return timeOfPreparation;
    }

    public void setTimeOfPreparation(String timeOfPreparation) {
        this.timeOfPreparation = timeOfPreparation;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
