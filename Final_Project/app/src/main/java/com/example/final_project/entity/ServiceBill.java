package com.example.final_project.entity;

public class ServiceBill {
    private int id, bill_id,service_id,service_quantity;
    private String service_date;
    private int total;

    public ServiceBill() {
    }

    public ServiceBill(int id, int bill_id, int service_id, int service_quantity, String service_date, int total) {
        this.id = id;
        this.bill_id = bill_id;
        this.service_id = service_id;
        this.service_quantity = service_quantity;
        this.service_date = service_date;
        this.total = total;
    }

    public ServiceBill(int service_id, int service_quantity, int total) {
        this.service_id = service_id;
        this.service_quantity = service_quantity;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBill_id() {
        return bill_id;
    }

    public void setBill_id(int bill_id) {
        this.bill_id = bill_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getService_quantity() {
        return service_quantity;
    }

    public void setService_quantity(int service_quantity) {
        this.service_quantity = service_quantity;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
