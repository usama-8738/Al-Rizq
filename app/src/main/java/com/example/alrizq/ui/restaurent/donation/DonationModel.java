package com.example.alrizq.ui.restaurent.donation;

public class DonationModel {

    String id, uId, itemName, image, TimeOfPreparation, Quantity, Address, utensil, status, createdAt;

    public DonationModel() {
    }

    public DonationModel(String id, String uId, String itemName, String image, String timeOfPreparation, String quantity, String address, String utensil, String status, String createdAt) {
        this.id = id;
        this.uId = uId;
        this.itemName = itemName;
        this.image = image;
        TimeOfPreparation = timeOfPreparation;
        Quantity = quantity;
        Address = address;
        this.utensil = utensil;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeOfPreparation() {
        return TimeOfPreparation;
    }

    public void setTimeOfPreparation(String timeOfPreparation) {
        TimeOfPreparation = timeOfPreparation;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUtensil() {
        return utensil;
    }

    public void setUtensil(String utensil) {
        this.utensil = utensil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
