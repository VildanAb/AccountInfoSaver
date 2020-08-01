package com.example.accountinfosaver.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountinfosaver.DATA.MainViewModel;
import com.example.accountinfosaver.Preference;
import com.example.accountinfosaver.R;

public class PasswordActivity extends AppCompatActivity {

    private EditText pin;
    private Button enterPin;

    private MainViewModel viewModel;
    private FingerprintManagerCompat.AuthenticationResult result;
    private Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        preference = new Preference(this);

        if(preference.getPin().equals("")){
            Intent intent = new Intent(PasswordActivity.this, SetPasswordActivity.class);
            startActivity(intent);
        }

        enterPin = findViewById(R.id.button10);
        pin = findViewById(R.id.editTextPin);
        pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        Intent intent = getIntent();
        final boolean flag = intent.getBooleanExtra("itsPC", false);
        if(flag){
            TextView title = findViewById(R.id.textViewTitle);
            title.setText("Введите старый пароль");
        }

        enterPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pin.getText().toString().trim().equals(preference.getPin())){
                    if(flag){
                        Intent intent = new Intent(PasswordActivity.this, SetPasswordActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(PasswordActivity.this, "Пароль не верен", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void resetPasswordOnClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Удалить всю информацию о сохраненных аккаунтах и очистить пароль?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.deleteAllAccounts();
                preference.setPin("");
                Intent intent = new Intent(PasswordActivity.this, SetPasswordActivity.class);
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

    public void hidePassword(View view) {
        if (pin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            pin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }



}
