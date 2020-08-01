package com.example.accountinfosaver.activities;

import
        androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountinfosaver.R;
import com.example.accountinfosaver.passwordGenerator.PasswordGenerator;



public class PasswordGeneratorActivity extends AppCompatActivity {

    private TextView passwordResult;

    private CheckBox useDigits;
    private CheckBox useLower;
    private CheckBox useUpper;

    private EditText passwordLength;

    private Button generateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_generator);

        passwordResult = findViewById(R.id.textViewPasswordGeneratorResult);

        useDigits = findViewById(R.id.checkBoxUseDigits);
        useLower = findViewById(R.id.checkBoxUseLower);
        useUpper = findViewById(R.id.checkBoxUseUpper);

        useDigits.setChecked(true);

        generateButton = findViewById(R.id.button);

        passwordLength = findViewById(R.id.EditTextPasswordLength);
    }

    public void generatePassword(View view) {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().build();
//                .useDigits(true)
//                .useLower(true)
//                .useUpper(true)
//                .build();
        if(useDigits.isChecked()){
            passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true).build();

        }
        if(useLower.isChecked()){
            passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useLower(true).build();

        }
        if(useUpper.isChecked()){
            passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useUpper(true).build();

        }
        if(useDigits.isChecked() && useUpper.isChecked()){
            passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true).useUpper(true).build();

        }
        if(useDigits.isChecked() && useLower.isChecked()){
            passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useDigits(true).useLower(true).build();

        }
        if(useLower.isChecked() && useUpper.isChecked()){
            passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useLower(true).useUpper(true).build();

        }
        if(useLower.isChecked() && useUpper.isChecked() && useDigits.isChecked()){
            passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder().useLower(true).useDigits(true).useUpper(true).build();

        }
        if(!useLower.isChecked() && !useUpper.isChecked() && !useDigits.isChecked()){
            Toast.makeText(this, "Выберите как минимум 1 пункт", Toast.LENGTH_SHORT).show();

        }else {
            int passwordLen = 8;
            try {
                passwordLen = Integer.parseInt(passwordLength.getText().toString());
            }catch (NumberFormatException e){
                Toast.makeText(this, "Длина пароля должна содержать цифры", Toast.LENGTH_SHORT).show();
            }
            if (passwordLen > 32) {
                Toast.makeText(this, "Длина пароля не больше 32 символов!", Toast.LENGTH_SHORT).show();
                passwordLength.setText("32");
                passwordLen = 32;
            }
            if (passwordLen == 0) {
                Toast.makeText(this, "Длина пароля изменена на 8", Toast.LENGTH_SHORT).show();
                passwordLength.setText("8");
                passwordLen = 8;
            }

            String password = passwordGenerator.generate(passwordLen);
            passwordResult.setText(password);
        }

    }
}
