package com.example.final_project.entity;

public class Room {
    private int id;
    private String name;
    private int roomTypeId;
    private int price;
    private int statusId;

    public Room() {
    }

    public Room(int id, String name, int roomTypeId, int price, int status) {
        this.id = id;
        this.name = name;
        this.roomTypeId = roomTypeId;
        this.price = price;
        this.statusId = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return statusId;
    }

    public void setStatus(int status) {
        this.statusId = status;
    }

}
