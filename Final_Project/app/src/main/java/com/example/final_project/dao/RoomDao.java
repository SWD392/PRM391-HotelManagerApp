package com.example.final_project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.database.DbHelper;
import com.example.final_project.entity.Room;
import com.example.final_project.entity.RoomType;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class RoomDao {

    private DbHelper dbHelper;
    private SQLiteDatabase db;


    public RoomDao(Context context) {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<Room> getDaTa(String sql, String... selectionArgs) {
        List<Room> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                int d = c.getInt(1);
                String b = c.getString(2);
                int g = c.getInt(3);
                int p = c.getInt(4);
                list.add(new Room(a, b, d, g, p));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }

    public Room getID(String id){
        String sql="select * from Room where id=?";
        List<Room> list = getDaTa(sql,id);
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
    public  List<Room> getListRoom(){
        String query = "SELECT * FROM Room";
        return getDaTa(query);
    }
    public long insertRoom(Room room){
        ContentValues values=new ContentValues();
        values.put("room_type_id",room.getRoomTypeId());
        values.put("name",room.getName());
        values.put("price",room.getPrice());
        values.put("status_id",room.getStatus());


        return db.insert("Room",null,values);

    }

    public int update(Room room){
        ContentValues values = new ContentValues();
        values.put("name", room.getName());
        values.put("room_type_id", room.getRoomTypeId());
        values.put("price", room.getPrice());
        values.put("status_id", room.getStatus());
        return db.update("Room", values,"id= ?",new String[]{room.getId()+""});
    }

    public void insert(String name, int price, int status, int room_type_id) {

        // calling a method to get writable database.
        ContentValues values = new ContentValues();


        values.put("name", name);
        values.put("price", price);
        values.put("status_id", status);
        values.put("room_type_id", room_type_id);
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.insert("Room",null, values);

    }
    public int delete(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM Bill WHERE room_id=?",new String[]{String.valueOf(id)});
        if (cursor.getCount()!=0){
            return -1 ;
        }
        long  check = db.delete("Room","id=?",new String[]{String.valueOf(id)});
        if(check==-1){
            return  0 ;
        }
        return 1 ;
    }
}
