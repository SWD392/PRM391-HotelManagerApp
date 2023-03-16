package com.example.final_project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.database.DbHelper;
import com.example.final_project.entity.Room;
import com.example.final_project.entity.RoomType;

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

    public List<Room> getListRoom() {
        String query = "SELECT * FROM Room ";
        return getDaTa(query);
    }

    public long insertRoom(Room room) {
        ContentValues values = new ContentValues();
        values.put("status_id", room.getStatus());
        values.put("name", room.getName());
        values.put("price", room.getPrice());
        values.put("room_category_id", room.getRoomTypeId());
        return db.insert("Room", null, values);
    }

    public List<Room> getRoomById(int id) {
        String query = "SELECT * FROM Room where id = " + id;
        return getDaTa(query);
    }
}
