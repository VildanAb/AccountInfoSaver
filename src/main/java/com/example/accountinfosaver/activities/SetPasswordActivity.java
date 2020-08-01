package com.example.accountinfosaver.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.accountinfosaver.Preference;
import com.example.accountinfosaver.R;

public class SetPasswordActivity extends AppCompatActivity {

    private static final String KEY_ALIAS = "key";
    private EditText fPin;
    private EditText sPin;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        final Preference preference = new Preference(this);
        fPin = findViewById(R.id.editTextPin);
        sPin = findViewById(R.id.editTextPinConfirm);
        button = findViewById(R.id.button10);
        fPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        sPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (fPin.getText().toString().trim().equals(sPin.getText().toString().trim())) {
                    if (fPin.getText().toString().length() >= 4 && fPin.getText().toString().length() >= 4) {
                        preference.setPin(fPin.getText().toString().trim());
                        Intent intent = new Intent(SetPasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SetPasswordActivity.this, "Длина пароля должна быть больше 4 символов", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SetPasswordActivity.this, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void hidePassword(View view) {
        if (fPin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            fPin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            fPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void hidePassword2(View view) {
        if (sPin.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            sPin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            sPin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

}
