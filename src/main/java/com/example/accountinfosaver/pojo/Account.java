package com.example.accountinfosaver.pojo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.accountinfosaver.BitmapConventer;

import java.io.ByteArrayOutputStream;

@Entity(tableName = "account_table")
public class Account {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String login;
    private String password;
    private String email;
    private String text;
    private String phone;
    private String accountType;
    private int spinnerPosition;
    private String imageResource;

    public Account(int id, String login, String password, String email, String text, String phone, String accountType, int spinnerPosition, String imageResource) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.text = text;
        this.phone = phone;
        this.accountType = accountType;
        this.spinnerPosition = spinnerPosition;
        this.imageResource = imageResource;
    }

    @Ignore
    public Account(String login, String password, String email, String text, String phone, String accountType, int spinnerPosition, String imageResource) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.text = text;
        this.phone = phone;
        this.accountType = accountType;
        this.spinnerPosition = spinnerPosition;
        this.imageResource = imageResource;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public int getSpinnerPosition() {
        return spinnerPosition;
    }

    public void setSpinnerPosition(int spinnerPosition) {
        this.spinnerPosition = spinnerPosition;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
