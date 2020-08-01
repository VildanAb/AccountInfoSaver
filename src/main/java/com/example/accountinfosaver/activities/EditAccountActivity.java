package com.example.accountinfosaver.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.example.accountinfosaver.DATA.MainViewModel;
import com.example.accountinfosaver.R;
import com.example.accountinfosaver.pojo.Account;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class EditAccountActivity extends AppCompatActivity {

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


    private boolean flag;

    private int position;

    private Uri imageUri;

    private boolean switchIsChecked;

    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        passwordEditText = findViewById(R.id.editTextPassword);
        loginEditText = findViewById(R.id.editTextLogin);
        emailEditText = findViewById(R.id.editTextEmail);
        textEditText = findViewById(R.id.EditTextText);
        phoneEditText = findViewById(R.id.EditTextPhone);

        deleteImage = findViewById(R.id.deleteImage);

        switchImage = findViewById(R.id.imageSwitch);
        switchTextView = findViewById(R.id.SwitchText);

        spinnerAccountType = findViewById(R.id.SpinnerAccountType);

        addImageImageView = findViewById(R.id.addImageImageView);
        addImageTextView = findViewById(R.id.addImageTextView);

        Intent intent = getIntent();
        position = intent.getIntExtra("position1", -1);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                final Account account = accounts.get(position);
                if (account.getImageResource() != null) {
                    uri = account.getImageResource();
                    addImageImageView.setVisibility(View.VISIBLE);
                    Glide.with(getApplicationContext()).load(account.getImageResource()).into(addImageImageView);
                    deleteImage.setVisibility(View.VISIBLE);
                    deleteImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            account.setImageResource(null);
                            addImageImageView.setImageResource(R.drawable.add_new_icon);
                            addImageTextView.setVisibility(View.VISIBLE);
                            deleteImage.setVisibility(View.GONE);
                        }
                    });
                    switchImage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                addImageImageView.setImageResource(R.drawable.add_new_icon);
                                addImageImageView.setVisibility(View.VISIBLE);
                                addImageTextView.setVisibility(View.VISIBLE);
                                deleteImage.setVisibility(View.GONE);
                                switchIsChecked = true;
                            } else {
                                addImageImageView.setVisibility(View.GONE);
                                deleteImage.setVisibility(View.GONE);
                                addImageImageView.setImageResource(R.drawable.add_new_icon);
                                addImageTextView.setVisibility(View.GONE);
                                imageUri = Uri.parse(Uri.decode(account.getImageResource()));
                                switchIsChecked = false;
                            }
                        }
                    });
                }
                passwordEditText.setText(account.getPassword());
                loginEditText.setText(account.getLogin());
                if (!account.getEmail().equals("без значения")) {
                    emailEditText.setText(account.getEmail());
                }
                if (!account.getText().equals("без значения")) {
                    textEditText.setText(account.getText());
                }
                if (!account.getPhone().equals("без значения")) {
                    phoneEditText.setText(account.getPhone());
                }
                spinnerAccountType.setSelection(account.getSpinnerPosition());

            }
        });

        flag = true;
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
                if (isChecked) {
                    addImageImageView.setVisibility(View.VISIBLE);
                    addImageTextView.setVisibility(View.VISIBLE);
                    switchIsChecked = true;
                } else {
                    addImageImageView.setVisibility(View.GONE);
                    deleteImage.setVisibility(View.GONE);
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
                if (imageUri != null) {
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
                if (switchIsChecked) {
                    switchImage.setChecked(false);
                } else {
                    switchImage.setChecked(true);
                }
            }
        });
//        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (spinnerAccountType.getSelectedItem().toString().equals("Другое")) {
//                    addImageImageView.setVisibility(View.VISIBLE);
//                    addImageTextView.setVisibility(View.VISIBLE);
//                } else {
//                    addImageImageView.setVisibility(View.GONE);
//                    addImageTextView.setVisibility(View.GONE);
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

    public void editAccountOnDB(View view) {
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
            if (phone.equals("")) {
                phone = "без значения";
            }
            Account account = null;

            if (imageUri == null) {
                account = new Account(login, password, email, text, phone, accountType, spinnerAccountType.getSelectedItemPosition(), null);
            }else if(uri != null){
                account = new Account(login, password, email, text, phone, accountType, spinnerAccountType.getSelectedItemPosition(), uri);
            } else {
                account = new Account(login, password, email, text, phone, accountType, spinnerAccountType.getSelectedItemPosition(), imageUri.toString());
            }
//            final Account account = new Account(login, password, email, text, phone, accountType, spinnerAccountType.getSelectedItemPosition());
            viewModel.insertAccount(account);

            final ArrayList<Account> accountsList = new ArrayList<>();



                   viewModel.getAccounts().observe(this, new Observer<List<Account>>() {
                       @Override
                       public void onChanged(List<Account> accounts) {
                           Account account1 = accounts.get(position);
                           if (flag)
                            viewModel.deleteAccount(account1);
                            flag = false;
                       }
                   });

            Intent intentToAccountInfo = new Intent(EditAccountActivity.this, MainActivity.class);
            startActivity(intentToAccountInfo);
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
