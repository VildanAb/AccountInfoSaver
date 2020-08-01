package com.example.accountinfosaver;

import android.app.Application;

import com.balram.locker.utils.Locker;
import com.balram.locker.view.AppLocker;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLocker.getInstance().enableAppLock(this);
    }
}
