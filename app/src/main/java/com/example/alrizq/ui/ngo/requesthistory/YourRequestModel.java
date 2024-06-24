package com.example.alrizq.ui.ngo.requesthistory;

import java.io.Serializable;

public class YourRequestModel implements Serializable {

    String description, needBefore, ngoName, acceptName, assignName, address, id, itemName, quantity, status, timeOfPreparation, uId, createdAt;

    public YourRequestModel() {
    }

    public YourRequestModel(String description, String needBefore, String ngoName, String acceptName, String assignName, String address, String id, String itemName, String quantity, String status, String timeOfPreparation, String uId, String createdAt) {
        this.description = description;
        this.needBefore = needBefore;
        this.ngoName = ngoName;
        this.acceptName = acceptName;
        this.assignName = assignName;
        this.address = address;
        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.status = status;
        this.timeOfPreparation = timeOfPreparation;
        this.uId = uId;
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNeedBefore() {
        return needBefore;
    }

    public void setNeedBefore(String needBefore) {
        this.needBefore = needBefore;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getAssignName() {
        return assignName;
    }

    public void setAssignName(String assignName) {
        this.assignName = assignName;
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
