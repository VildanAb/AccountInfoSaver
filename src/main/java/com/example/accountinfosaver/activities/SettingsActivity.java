package com.example.accountinfosaver.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.balram.locker.utils.Locker;
import com.balram.locker.view.AppLocker;
import com.balram.locker.view.LockActivity;
import com.example.accountinfosaver.DATA.MainViewModel;
import com.example.accountinfosaver.R;
import com.example.accountinfosaver.adapters.SettingsAdapter;
import com.example.accountinfosaver.pojo.Setting;

import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {

    private RecyclerView settingsRecyclerView;

    private SettingsAdapter adapter;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        adapter = new SettingsAdapter(this);

        settingsRecyclerView = findViewById(R.id.settingsRecyclerView);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        ArrayList<Setting> settings = new ArrayList<>();

        Setting setting = new Setting(R.drawable.ic_delete_blue_24dp, "Очистить данные");
        Setting setting2 = new Setting(R.drawable.ic_lock_black_24dp, "Изменить пароль");
        settings.add(setting);
        settings.add(setting2);
        adapter.notifyDataSetChanged();

        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setSettings(settings);

        settingsRecyclerView.setAdapter(adapter);

        adapter.setSettingsOnClickListener(new SettingsAdapter.SettingsOnClickListener() {
            @Override
            public void onSettingClick(int position, Context context) {
                Setting setting1 = adapter.getSettings().get(position);
                if (setting1.getTitle().equals("Очистить данные")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Удалить всю информацию о сохраненных аккаунтах?");
                    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewModel.deleteAllAccounts();
                            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                if (setting1.getTitle().equals("Изменить пароль")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Изменить пароль?");
                    builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SettingsActivity.this, PasswordActivity.class);
                            intent.putExtra("itsPC", true);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }


            }
        });
    }
}

