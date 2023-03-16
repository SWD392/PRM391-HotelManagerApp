package com.example.final_project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.database.DbHelper;
import com.example.final_project.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    public CustomerDAO(Context context){
        dbHelper= new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }
    public List<Customer> getDaTa(String sql, String...selectionArgs){
        List<Customer> list=new ArrayList<>();
        Cursor cr=db.rawQuery(sql,selectionArgs);
        if (cr.getCount() > 0) {
            cr.moveToFirst();
            while (!cr.isAfterLast()) {
                int a = cr.getInt(0);
                String b= cr.getString(1);
                String c = cr.getString(2);
                String d=cr.getString(3);
                list.add(new Customer(a,b,c,d));
                cr.moveToNext();
            }
            cr.close();
        }
        return list;
    }
    public  List<Customer> getListCustomer(){
        String query = "SELECT * FROM Customer ";
        return getDaTa(query);
    }
    public  List<Customer> searchCustomer(String name){
        String query = "SELECT * FROM Customer where name like "+name;
        return getDaTa(query);
    }
    public long addCustomer(Customer customer){
        ContentValues values=new ContentValues();
        values.put("name",customer.getName());
        values.put("phone_number",customer.getPhone());
        values.put("birthday",customer.getBirthday());
        return db.insert("Customer",null,values);
    }
    public int deleteCus(int id){
//        Cursor cursor = db.rawQuery("SELECT * FROM Bills WHERE guest_id = ?",new String[]{String.valueOf(id)});
//        if (cursor.getCount()!=0){
//            return -1 ;
//        }
        long  check = db.delete("Customer","id=?",new String[]{String.valueOf(id)});
        if(check==-1){
            return  0 ;
        }
        return 1 ;
    }
    public long update(Customer cus){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",cus.getName());
        contentValues.put("phone_number",cus.getPhone());
        contentValues.put("birthday",String.valueOf(cus.getBirthday()));
        long res = db.update("Customer",contentValues,"id=?",new String[]{cus.getId()+""});
        return res ;
    }
}
