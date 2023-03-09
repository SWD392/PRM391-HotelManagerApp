package com.example.final_project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "Hotel_Management";

    static final int DATABASE_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUserAccount = "create table UserAccount(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT, " +
                "account TEXT NOT NULL, " +
                "password TEXT NOT NULL )";
        db.execSQL(createTableUserAccount);

        String createTableCustomer = "create table Customer (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone_number TEXT NOT NULL," +
                "birthday DATE NOT NULL )";
        db.execSQL(createTableCustomer);

        String createTableRoomType = "create table RoomType (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)";
        db.execSQL(createTableRoomType);

        String createTableRoomStatus = "create table RoomStatus (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL)";
        db.execSQL(createTableRoomStatus);

        String createTableRoom = "create table Room (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "room_category_id INTERGER REFERENCES RoomType(id)," +
                "name TEXT NOT NULL," +
                "price INTERGER NOT NULL ," +
                "status_id INTERGER REFERENCES RoomStatus(id))";
        db.execSQL(createTableRoom);

        String createTableService = "create table Service (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "price INTEGER NOT NULL)";
        db.execSQL(createTableService);

        //todo: cái này liên quan đến service, a Dương để ý dùm e!
//        String createTableBill_Details = "create table Service_bills (" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "bill_id INTEGER REFERENCES Bills(id)," +
//                "service_id INTEGER REFERENCES Services(id)," +
//                "service_quantity INTEGER NOT NULL," +
//                "service_date Date NOT NULL," +
//                "total INTEGER NOT NULL)";
//        db.execSQL(createTableBill_Details);

        String createTableBill = "create table Bill (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "manager_id TEXT REFERENCES UserAccount(id)," +
                "customer_id INTEGER REFERENCES Customer(id)," +
                "room_id INTEGER REFERENCES Room(id)," +
                "start_date DATE NOT NULL," +
                "end_date DATE NOT NULL," +
                "status INTEGER NOT NULL," +
                "note TEXT ," +
                "bill_date DATE NOT NULL," +
                "service_total INTERGER NOT NULL," +
                "room_total INTERGER NOT NULL," +
                "bill_total INTEGER NOT NULL)";
        db.execSQL(createTableBill);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS UserAccount");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS RoomStatus");
        db.execSQL("DROP TABLE IF EXISTS Room");
        db.execSQL("DROP TABLE IF EXISTS Room_Type");
        db.execSQL("DROP TABLE IF EXISTS Bill");
//        db.execSQL("DROP TABLE IF EXISTS Bill_Details");
        db.execSQL("DROP TABLE IF EXISTS Service");
    }
}
