package com.example.final_project.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userAccount")
public class UserAccount {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "username")
    public String userName;

    @ColumnInfo(name = "account")
    public String account;

    @ColumnInfo(name = "password")
    public String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
