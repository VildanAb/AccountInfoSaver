package com.example.accountinfosaver.activities;


import android.content.Intent;
import android.os.Bundle;

import com.balram.locker.utils.Locker;
import com.balram.locker.view.AppLocker;
import com.balram.locker.view.LockActivity;
import com.example.accountinfosaver.R;

public class StartingActivity extends LockActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        if(!AppLocker.getInstance().isAppLockEnabled()) {
            Intent intent = new Intent(this, LockActivity.class);
            intent.putExtra(Locker.TYPE, Locker.ENABLE_PASSLOCK);
            startActivityForResult(intent, Locker.ENABLE_PASSLOCK);
        }else{

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Locker.ENABLE_PASSLOCK){
            if(resultCode == RESULT_OK){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
