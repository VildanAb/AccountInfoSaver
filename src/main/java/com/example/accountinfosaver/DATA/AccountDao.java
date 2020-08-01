package com.example.accountinfosaver.DATA;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.accountinfosaver.pojo.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM account_table")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT * FROM account_table WHERE id = :position")
    Account getAccountById(int position);

    @Insert
    void insertAccount(Account account);

    @Delete
    void deleteAccount(Account account);

    @Query("DELETE FROM account_table")
    void deleteAllAccount();
}
