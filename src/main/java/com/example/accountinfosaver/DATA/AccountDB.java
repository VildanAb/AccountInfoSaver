package com.example.accountinfosaver.DATA;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.accountinfosaver.pojo.Account;

@Database(entities = {Account.class}, version = 9, exportSchema = false)
public abstract class AccountDB extends RoomDatabase {

    private static AccountDB accountDB;
    private static final String DB_NAME = "account.db";
    private static final Object lock = new Object();

    static AccountDB getInstance(Context context){
        synchronized (lock) {
            if (accountDB == null) {
                accountDB = Room.databaseBuilder(context, AccountDB.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return accountDB;
    }

    public abstract AccountDao accountDao();
}
