package com.example.final_project.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.database.DbHelper;
import com.example.final_project.entity.RoomType;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeDao

{
    private DbHelper dbHelper;
    private SQLiteDatabase db;


    public RoomTypeDao(Context context){
        dbHelper= new DbHelper(context);
        db=dbHelper.getWritableDatabase();
    }

    //Insert
    public long insertRoomType(RoomType roomType){
        ContentValues values= new ContentValues();
        values.put("name",roomType.getName());
        return db.insert("RoomType",null,values);
    }
public int updateRoomTypeName(RoomType roomType){
        ContentValues values = new ContentValues();
        values.put("name",roomType.getName());
        return db.update("RoomType",values,"id=?",new String[]{roomType.getId()+""});
}

public int deleteRoomType(int  id){
        Cursor cursor = db.rawQuery("SELECT * FROM Room WHERE id=?",new String[]{String.valueOf(id)});
        if(cursor.getCount()!=0){
            return 2;
        }
        long check =db.delete("RoomType","id=?",new String[]{String.valueOf(id)});
        if(check==2){
            return 0;
        }
        else {
            return 1;
        }
}
    public List<RoomType> getDaTa(String sql, String...selectionArgs){
        List<RoomType> list=new ArrayList<>();
        Cursor c=db.rawQuery(sql,selectionArgs);
        if (c.getCount() > 0) {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int a = c.getInt(0);
                String b = c.getString(1);
                list.add(new RoomType(a,b));
                c.moveToNext();
            }
            c.close();
        }
        return list;
    }
    public  List<RoomType> getListRoomType(){
        String query = "SELECT * FROM RoomType ";
      return getDaTa(query);
    }

    public RoomType getID(String id){
        String sql = "SELECT * FROM RoomType WHERE id=?";
        List<RoomType> list = getDaTa(sql,id);
        return list.get(0);
    }

}
