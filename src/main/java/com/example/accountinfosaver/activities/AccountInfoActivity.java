package com.example.accountinfosaver.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.accountinfosaver.DATA.MainViewModel;
import com.example.accountinfosaver.R;
import com.example.accountinfosaver.pojo.Account;

import java.util.List;


public class AccountInfoActivity extends AppCompatActivity {

    private TextView loginTextView;
    private TextView passwordTextView;
    private TextView emailTextView;
    private TextView textTextView;
    private TextView loginLabelTextView;
    private TextView phoneTextView;
    private TextView accountTextView;
    private ImageView icon;

    MainViewModel viewModel;

    int position;
    private boolean flag;
    private boolean isHide = true;
    private String password;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_menu_item) {
            Intent intent = new Intent(AccountInfoActivity.this, EditAccountActivity.class);
            intent.putExtra("position1", position);
            intent.putExtra("hide/visible", -1);
            intent.putExtra("spinnerValue", accountTextView.getText().toString());
            startActivity(intent);
        } else if (item.getItemId() == R.id.delete_menu_item) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Удалить данные об аккаунте?");
            builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteMethod();
//
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);


        loginTextView = findViewById(R.id.textViewLoginValue);
        passwordTextView = findViewById(R.id.textViewPasswordValue);
        emailTextView = findViewById(R.id.textViewEmailValue);
        textTextView = findViewById(R.id.textViewTextValue);
        loginLabelTextView = findViewById(R.id.textView6);
        phoneTextView = findViewById(R.id.textViewPhoneValue);
        accountTextView = findViewById(R.id.textViewAccountTypeValue);

        icon = findViewById(R.id.imageViewIcon);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        flag = true;


        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);


        viewModel.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                Account account = accounts.get(position);
                loginLabelTextView.setText(account.getLogin());
                loginTextView.setText(account.getLogin());
                passwordTextView.setText(account.getPassword());
                password = passwordTextView.getText().toString();
                hidePass();
                emailTextView.setText(account.getEmail());
                textTextView.setText(account.getText());
                phoneTextView.setText(account.getPhone());
                accountTextView.setText(account.getAccountType());
                if (account.getImageResource() != null) {
                    Uri uri = Uri.parse(Uri.decode(account.getImageResource()));
                    Glide.with(getApplicationContext()).load(uri).into(icon);
                } else {
                    if (account.getAccountType().equals("Steam")) {
                        icon.setImageResource(R.drawable.steam);
                    } else if (account.getAccountType().equals("Google")) {
                        icon.setImageResource(R.drawable.google);
                    } else if (account.getAccountType().equals("VK")) {
                        icon.setImageResource(R.drawable.vk);
                    } else if (account.getAccountType().equals("Instagram")) {
                        icon.setImageResource(R.drawable.instagram);
                    } else if (account.getAccountType().equals("Telegram")) {
                        icon.setImageResource(R.drawable.telegram);
                    } else if (account.getAccountType().equals("Epic Games Store")) {
                        icon.setImageResource(R.drawable.epic);
                    } else if (account.getAccountType().equals("Yandex")) {
                        icon.setImageResource(R.drawable.yandex);
                    } else if (account.getAccountType().equals("Facebook")) {
                        icon.setImageResource(R.drawable.facebook);
                    } else if (account.getAccountType().equals("PornHub")) {
                        icon.setImageResource(R.drawable.ph);
                    } else if (account.getAccountType().equals("Другое")) {
                        icon.setImageResource(R.drawable.other);
                    } else {
                        finish();
                    }
                }
            }
        });

    }


    public void deleteMethod() {

        viewModel.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                Account accountToDelete = accounts.get(position);
                if (accounts.size() - 1 == position) {
                    position = 0;
                }
                if (flag) {
                    viewModel.deleteAccount(accountToDelete);
                    flag = false;
                    Intent intent = new Intent(AccountInfoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    public void hidePasswordAsString(View view) {
        if (isHide) {
            isHide = false;
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <= password.length() - 1; i++) {
                stringBuilder.append("*");
            }
            passwordTextView.setText(stringBuilder.toString());
        } else {
            passwordTextView.setText(password);
            isHide = true;
        }
    }

    public void hidePass() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= password.length() - 1; i++) {
            stringBuilder.append("*");
        }
        passwordTextView.setText(stringBuilder.toString());
        isHide = false;
    }
}
