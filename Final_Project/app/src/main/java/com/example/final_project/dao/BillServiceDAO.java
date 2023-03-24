package com.example.final_project.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.final_project.database.DbHelper;
import com.example.final_project.entity.ServiceBill;

import java.util.ArrayList;
import java.util.List;

public class BillServiceDAO {
    private SQLiteDatabase db;

    public BillServiceDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ServiceBill serviceBill){
        ContentValues values = new ContentValues();
        values.put("bill_id",serviceBill.getBill_id());
        values.put("service_id",serviceBill.getService_id());
        values.put("service_quantity",serviceBill.getService_quantity());
        values.put("service_date",String.valueOf(serviceBill.getService_date()));
        values.put("total",serviceBill.getTotal());
        return db.insert("Service_bills",null,values);
    }

    public long update(ServiceBill serviceBill){
        ContentValues values = new ContentValues();
        values.put("bill_id",serviceBill.getBill_id());
        values.put("service_id",serviceBill.getService_id());
        values.put("service_quantity",serviceBill.getService_quantity());
        values.put("service_date",String.valueOf(serviceBill.getService_date()));
        values.put("total",serviceBill.getTotal());
        return db.update("Service_bills",values,"id=?",new String[]{String.valueOf(serviceBill.getId())});
    }

    public List<ServiceBill> getAll(){
        String sql = "SELECT * FROM Service_bills ORDER BY service_date DESC";
        return getData(sql);
    }
    public ServiceBill getId(String id){
        String sql = "SELECT * FROM Service_bills WHERE id=?";
        List<ServiceBill> list=getData(sql,id);
        return list.get(0);
    }

    public int delete(int s){
        return db.delete("Service_bills","id=?",new String[]{s+""});
    }


    @SuppressLint("Range")
    private List<ServiceBill> getData(String sql, String...selectionArgs) {

        List<ServiceBill> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ServiceBill serviceBill = new ServiceBill();
            serviceBill.setId(Integer.parseInt(c.getString(c.getColumnIndex("id"))));
            serviceBill.setBill_id(Integer.parseInt(c.getString(c.getColumnIndex("bill_id"))));
            serviceBill.setService_id(Integer.parseInt(c.getString(c.getColumnIndex("service_id"))));
            serviceBill.setService_quantity(Integer.parseInt(c.getString(c.getColumnIndex("service_quantity"))));
            serviceBill.setService_date(c.getString(c.getColumnIndex("service_date")));
            serviceBill.setTotal(Integer.parseInt(c.getString(c.getColumnIndex("total"))));
            list.add(serviceBill);
        }
        return list;

    }
}
