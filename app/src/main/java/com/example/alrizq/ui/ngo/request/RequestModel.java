package com.example.alrizq.ui.ngo.request;

import java.io.Serializable;

public class RequestModel implements Serializable {

    String id, ngoName, uId, needBefore, quantity, address, status, description, createdAt;

    public RequestModel() {

    }

    public RequestModel(String id, String ngoName, String uId, String needBefore, String quantity, String address, String status, String description, String createdAt) {
        this.id = id;
        this.ngoName = ngoName;
        this.uId = uId;
        this.needBefore = needBefore;
        this.quantity = quantity;
        this.address = address;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNeedBefore() {
        return needBefore;
    }

    public void setNeedBefore(String needBefore) {
        this.needBefore = needBefore;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
