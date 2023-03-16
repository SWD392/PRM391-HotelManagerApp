package com.example.final_project.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.database.DbHelper;
import com.example.final_project.entity.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    private SQLiteDatabase db;

    public BillDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Bill bill) {
        ContentValues values = new ContentValues();
        values.put("id", (getLastBill().size() != 0 ? getLastBill().get(0).getId() : 0) + 1);
        values.put("customer_id", bill.getCustomerId());
        values.put("room_id", bill.getRoomId());
        values.put("from_date", String.valueOf(bill.getFromDate()));
        values.put("end_date", String.valueOf(bill.getEndDate()));
        values.put("status", bill.getStatus());
        values.put("note", bill.getNote());
        values.put("bill_date", String.valueOf(bill.getBillDate()));
        values.put("bill_total", bill.getBillTotal());
        values.put("service_price", bill.getServicePrice());
        values.put("room_price", bill.getRoomPrice());

        return db.insert("Bill", null, values);
    }

    public boolean update(Bill bill) {
        ContentValues values = new ContentValues();
        values.put("customer_id", bill.getCustomerId());
        values.put("room_id", bill.getRoomId());
        values.put("from_date", String.valueOf(bill.getFromDate()));
        values.put("end_date", String.valueOf(bill.getEndDate()));
        values.put("status", bill.getStatus());
        values.put("note", bill.getNote());
        values.put("bill_date", String.valueOf(bill.getBillDate()));
        values.put("bill_total", bill.getBillTotal());
        values.put("service_price", bill.getServicePrice());
        values.put("room_price", bill.getRoomPrice());
        return db.update("Bill", values, "id = " + bill.getId(), null) > 0;
    }

    public boolean deleteBill(int id) {
        return db.delete("Bill", "id = " + id, null) > 0;
    }

    public List<Bill> getLastBill() {
        String query = "select * from bill order by id desc limit 1";
        return getData(query);
    }

    public List<Bill> getAll() {
        String query = "Select * from bill";
        return getData(query);
    }

    @SuppressLint({"Recycle", "Range"})
    public List<Bill> getData(String query, String... selectAgrs) {
        List<Bill> billList = new ArrayList<>();
        Cursor c = db.rawQuery(query, selectAgrs);
        while (c.moveToNext()) {
            Bill bill = new Bill();
            bill.setId(Integer.parseInt(c.getString(c.getColumnIndex("id"))));
            bill.setCustomerId(Integer.parseInt(c.getString(c.getColumnIndex("customer_id"))));
            bill.setRoomId(Integer.parseInt(c.getString(c.getColumnIndex("room_id"))));
            bill.setFromDate(c.getString(c.getColumnIndex("from_date")));
            bill.setEndDate(c.getString(c.getColumnIndex("end_date")));
            bill.setStatus(Integer.parseInt(c.getString(c.getColumnIndex("status"))));
            bill.setNote(c.getString(c.getColumnIndex("note")));
            bill.setBillDate(c.getString(c.getColumnIndex("bill_date")));
            bill.setServicePrice(Integer.parseInt(c.getString(c.getColumnIndex("service_price"))));
            bill.setRoomPrice(Integer.parseInt(c.getString(c.getColumnIndex("room_price"))));
            bill.setBillTotal(Integer.parseInt(c.getString(c.getColumnIndex("bill_total"))));
            billList.add(bill);
        }
        return billList;
    }

    public Bill getId(String id){
        String sql = "SELECT * FROM Bill WHERE id=?";
        List<Bill> list=getData(sql,id);
        return list.get(0);
    }
}
