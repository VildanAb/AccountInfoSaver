package com.example.accountinfosaver.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accountinfosaver.DATA.MainViewModel;
import com.example.accountinfosaver.R;

import com.example.accountinfosaver.pojo.Account;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;



public class CreateAccountInfoActivity extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText loginEditText;
    private EditText emailEditText;
    private EditText textEditText;
    private EditText phoneEditText;

    private TextView switchTextView;
    private Switch switchImage;

    private ImageView deleteImage;
    private ImageView addImageImageView;
    private TextView addImageTextView;

    private Spinner spinnerAccountType;

    private MainViewModel viewModel;

    private Button buttonCreate;
    private Button buttonEdit;
    private int position;
    private int flag;
    private boolean switchIsChecked;

    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        switchImage = findViewById(R.id.imageSwitch);
        switchTextView = findViewById(R.id.SwitchText);

        passwordEditText = findViewById(R.id.editTextPassword);
        loginEditText = findViewById(R.id.editTextLogin);
        emailEditText = findViewById(R.id.editTextEmail);
        textEditText = findViewById(R.id.EditTextText);
        phoneEditText = findViewById(R.id.EditTextPhone);

        addImageImageView = findViewById(R.id.addImageImageView);
        addImageTextView = findViewById(R.id.addImageTextView);

        deleteImage = findViewById(R.id.deleteImage);

        spinnerAccountType = findViewById(R.id.SpinnerAccountType);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        addImageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 101);

            }
        });

        switchImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    addImageImageView.setVisibility(View.VISIBLE);
                    addImageTextView.setVisibility(View.VISIBLE);
                    switchIsChecked = true;
                }else{
                    addImageImageView.setVisibility(View.GONE);
                    addImageImageView.setImageResource(R.drawable.add_new_icon);
                    imageUri = null;
                    addImageTextView.setVisibility(View.GONE);
                    switchIsChecked = false;
                }
            }
        });

        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    imageUri = null;
                    addImageImageView.setImageResource(R.drawable.add_new_icon);
                    addImageTextView.setVisibility(View.VISIBLE);
                    deleteImage.setVisibility(View.GONE);
                }
            }
        });

        switchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchIsChecked){
                    switchImage.setChecked(false);
                }else{
                    switchImage.setChecked(true);
                }
            }
        });

//        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(spinnerAccountType.getSelectedItem().toString().equals("Другое")){
//
//                }else if (spinnerAccountType.getSelectedItem().toString().equals("Google")){
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(addImageImageView);
                deleteImage.setVisibility(View.VISIBLE);
                addImageTextView.setVisibility(View.GONE);
//                try {
////                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
//            }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void hidePassword(View view) {
        if (passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void addAccountToDB(View view) {


        String login = loginEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String text = textEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String accountType = spinnerAccountType.getSelectedItem().toString();


        if (login != null && !login.equals("") && password != null && !password.equals("")) {
            if (email == null || email.equals("")) {
                email = "без значения";
            }
            if (text == null || text.equals("")) {
                text = "без значения";
            }
            if(phone.equals("")){
                phone = "без значения";
            }

            Account account = null;
            if(imageUri == null){
                account = new Account(login, password, email, text, phone, accountType, spinnerAccountType.getSelectedItemPosition(), null);
            }else{
                account = new Account(login, password, email, text, phone, accountType, spinnerAccountType.getSelectedItemPosition(), imageUri.toString());
            }
            viewModel.insertAccount(account);

            Intent intent = new Intent(CreateAccountInfoActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Заполните поля login или password", Toast.LENGTH_SHORT).show();
        }
    }

    public void hidePasswords(View view) {
        if (passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
            passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}

