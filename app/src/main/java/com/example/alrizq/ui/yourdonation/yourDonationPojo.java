package com.example.alrizq.ui.yourdonation;

import java.io.Serializable;

public class yourDonationPojo implements Serializable {

    String address,id,itemName,quantity,status, timeOfPreparation,uId, createdAt,image;

    public yourDonationPojo() {
    }

    public yourDonationPojo(String address, String id, String itemName, String quantity, String status, String timeOfPreparation, String uId, String createdAt, String image) {
        this.address = address;
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
        this.timeOfPreparation = timeOfPreparation;
        this.uId = uId;
        this.createdAt = createdAt;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
