package com.example.final_project.entity;

public class Room {
    private int id;
    private String name;
    private int room_type_id;
    private int price;
    private int status_id;

    public Room() {
    }

    public Room(int id, String name, int room_type_id, int price, int status) {
        this.id = id;
        this.name = name;
        this.room_type_id = room_type_id;
        this.price = price;
        this.status_id = status;
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

    public int getRoom_type_id() {
        return room_type_id;
    }

    public void setRoom_type_id(int room_type_id) {
        this.room_type_id = room_type_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status_id;
    }

    public void setStatus(int status) {
        this.status_id = status;
    }

}
