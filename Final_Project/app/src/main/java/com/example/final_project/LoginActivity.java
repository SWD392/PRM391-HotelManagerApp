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

        titleAccount = findViewById(R.id.title_account);
        titlePassword = findViewById(R.id.title_password);

        loginBtn = findViewById(R.id.btn_login);
        register = findViewById(R.id.tv_register);

        register.setOnClickListener(button -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(button -> {
            if (account.getText().toString().equals("") || password.getText().toString().equals("")) {
                if (account.getText().toString().equals("")) {
                    titleAccount.setError("Account was not blank");
                }
                if (password.getText().toString().equals("")) {
                    titlePassword.setError("Password was not blank");
                }
            } else {
                UserAccount userAccount = userAccountDatabase.userAccountDAO().getByAccountAndPassword(
                        account.getText().toString(),
                        password.getText().toString()
                );
                if (userAccount != null) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    titleAccount.setError("Account may not exist");
                    titlePassword.setError("Password may not exist");
                }
            }
        });

    }
}