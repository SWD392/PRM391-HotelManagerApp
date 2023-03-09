package com.example.final_project.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.final_project.entity.UserAccount;

import java.util.List;

@Dao
public interface UserAccountDAO {


    //todo: @Tùng sửa lại file này nhé

    @Query("Select * from userAccount where account = :account and password = :password")
    UserAccount getByAccountAndPassword(String account, String password);

    @Query("Select * from userAccount where account = :account")
    UserAccount getByAccount(String account);

    @Query(value = "Select * from userAccount")
    List<UserAccount> getAll();

    @Insert
    void insertAll(UserAccount... userAccounts);

//    @Query("Insert into UserAccount(account, password, userName) values (:account, :password, :userName)")
//    int insertUser(String account, String password, String userName);
}
