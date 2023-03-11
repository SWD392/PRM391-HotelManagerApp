package com.example.final_project.entity;

public class Bill {
    private int id;

    private int managerId;

    private int customerId;

    private int roomId;

    private String fromDate;

    private String endDate;

    private int status;

    private String note;

    private String billDate;

    private int servicePrice;

    private int roomPrice;

    private int billTotal;

    public Bill() {
    }

    public Bill(int id, int managerId, int customerId, int roomId, String fromDate, String endDate, int status, String note, String billDate, int servicePrice, int roomPrice, int billTotal) {
        this.id = id;
        this.managerId = managerId;
        this.customerId = customerId;
        this.roomId = roomId;
        this.fromDate = fromDate;
        this.endDate = endDate;
        this.status = status;
        this.note = note;
        this.billDate = billDate;
        this.servicePrice = servicePrice;
        this.roomPrice = roomPrice;
        this.billTotal = billTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public int getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }

    public int getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(int roomPrice) {
        this.roomPrice = roomPrice;
    }

    public int getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(int billTotal) {
        this.billTotal = billTotal;
    }
}
