package com.example.final_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.database.UserAccountDatabase;
import com.example.final_project.entity.UserAccount;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private EditText account, password;

    private TextInputLayout titleAccount, titlePassword;
    private Button loginBtn;
    private TextView register;
    private UserAccountDatabase userAccountDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userAccountDatabase = UserAccountDatabase.getInstance(this);

        account = findViewById(R.id.account_login);
        password = findViewById(R.id.password_login);

        titleAccount = findViewById(R.id.title_account_login);
        titlePassword = findViewById(R.id.til_password_login);

        loginBtn = findViewById(R.id.btn_login);
        register = findViewById(R.id.tv_register);

        register.setOnClickListener(button -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(button -> {
            if (account.getText().toString().equals("") || password.getText().toString().equals("")) {
                if (account.getText().toString().equals("")) {
                    titleAccount.setError("Account can not be blank!");
                }
                if (password.getText().toString().equals("")) {
                    titlePassword.setError("Password can not be blank!");
                }
            } else  {
                UserAccount userAccount = userAccountDatabase.userAccountDAO().getByAccountAndPassword(
                        account.getText().toString(),
                        password.getText().toString()
                );
                if (userAccount != null) {
                    Toast.makeText(this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    account.setError("Account may not exist");
                    password.setError("Password may not exist");

                }
            }
        });

    }
}