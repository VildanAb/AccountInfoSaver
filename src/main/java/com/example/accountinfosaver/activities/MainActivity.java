package com.example.accountinfosaver.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.balram.locker.utils.Locker;
import com.balram.locker.view.AppLocker;
import com.balram.locker.view.LockActivity;
import com.example.accountinfosaver.DATA.MainViewModel;
import com.example.accountinfosaver.R;
import com.example.accountinfosaver.adapters.AccountAdapter;

import com.example.accountinfosaver.pojo.Account;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public AccountAdapter adapter;
    final ArrayList<Account> accountsAL = new ArrayList<>();
    Context context = this;

    private MainViewModel viewModel;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.password_generator) {
            Intent intentToPG = new Intent(MainActivity.this, PasswordGeneratorActivity.class);
            startActivity(intentToPG);
        } else if (item.getItemId() == R.id.user_settings) {
            Intent intentToSettings = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentToSettings);
        }else{
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(!AppLocker.getInstance().isAppLockEnabled()) {
//            int type = AppLocker.getInstance().getAppLock().isPasscodeSet() ? Locker.DISABLE_PASSLOCK : Locker.ENABLE_PASSLOCK;
//            Intent intentLock = new Intent(this, LockActivity.class);
//            intentLock.putExtra(Locker.TYPE, type);
//            startActivityForResult(intentLock, type);
//        }

       storagePermission();

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                ArrayList arrayList = (ArrayList) accounts;
                adapter.setAccounts(arrayList);
            }
        });
        recyclerView = findViewById(R.id.accountRecyclerView);

        Intent intent = getIntent();

        adapter = new AccountAdapter(this);

        adapter.setAccounts(accountsAL);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        adapter.setAccountOnClickListener(new AccountAdapter.AccountOnClickListener() {
            @Override
            public void onAccountClick(int position) {
                Intent intent = new Intent(MainActivity.this, AccountInfoActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 104:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("При отказе в доступе приложения к данным, вы не сможете пользоваться загрузкой изображений, вместо иконок аккаунтов! Продолжить?");
                        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Дать разрешение", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                storagePermission();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
                return;
        }

        public void storagePermission(){
            int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                Log.i("PermissionForStorage", "есть разрешение");
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        104);
            }
        }

    public void toAddAccount(View view) {
        Intent intent = new Intent(MainActivity.this, CreateAccountInfoActivity.class);
        startActivity(intent);
    }
}
