package com.example.final_project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.database.DbHelper;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class TypeServiceDAO {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public TypeServiceDAO(Context context){
        dbHelper=new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    public long insert(ServiceType serviceType){
        ContentValues values=new ContentValues();
        values.put("name",serviceType.getName());
        values.put("price",serviceType.getPrice());
        return db.insert("Services",null,values);
    }
    public long update(ServiceType serviceType){
        ContentValues values=new ContentValues();
        values.put("name",serviceType.getName());
        values.put("price",serviceType.getPrice());
        String where = "id=?";
        String[] whereArgs = new String[] {String.valueOf(serviceType.getId())};
        return db.update("Services",values,where, whereArgs);
    }
    public long delete(int id){
        return db.delete("Services","id=?",new String[]{String.valueOf(id)});
    }

//    public int delete(int id){
//        Cursor cursor = db.rawQuery("SELECT * FROM Service_bills WHERE service_id=?",new String[]{String.valueOf(id)});
//        if (cursor.getCount()!=0){
//            return -1 ;
//        }
//        long check = db.delete("Services","id=?",new String[]{String.valueOf(id)});
//        if(check==-1){
//            return  0 ;
//        }
//        return 1 ;
//    }

    public List<ServiceType> getDaTa(String sql, String...selectionArgs){
        List<ServiceType> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                String b = c.getString(1);
                String price = c.getString(2);
                list.add(new ServiceType(a,b,price));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public List<ServiceType> getAll(){
        String sql="select * from Services";
        return getDaTa(sql);
    }

    public ServiceType getID(String id){
        String sql="select * from Services where id=?";
        List<ServiceType> list = getDaTa(sql,id);
        return list.get(0);
    }
}
