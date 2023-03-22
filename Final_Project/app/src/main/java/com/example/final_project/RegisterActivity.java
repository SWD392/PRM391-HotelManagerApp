package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.database.UserAccountDatabase;
import com.example.final_project.entity.UserAccount;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    EditText userName, account, password, confirmPassword;

    TextInputLayout titleUsername, titleAccount, titlePassword, titleConfirmPassword;
    Button registerBtn;
    TextView loginView;

    UserAccountDatabase userAccountDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.user_name_register);
        account = findViewById(R.id.account_register);
        password = findViewById(R.id.password_register);
        confirmPassword = findViewById(R.id.confirm_password_register);

        titleUsername = findViewById(R.id.title_username);
        titleAccount = findViewById(R.id.title_account);
        titlePassword = findViewById(R.id.title_password);
        titleConfirmPassword = findViewById(R.id.title_cf_password);

        registerBtn = findViewById(R.id.btn_register);
        loginView = findViewById(R.id.view_login);

        userAccountDatabase = UserAccountDatabase.getInstance(this);

        loginView.setOnClickListener(login -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        registerBtn.setOnClickListener(button -> {
            if (userName.getText().toString().equals("")
                    || account.getText().toString().equals("")
                    || password.getText().toString().equals("")
                    || confirmPassword.getText().toString().equals("")) {

                if (userName.getText().toString().equals("")) {
                    titleUsername.setError("Field Username cannot blank");
                } else {
                    titleUsername.setError("");
                }

                if (account.getText().toString().equals("")) {
                    titleAccount.setError("Field Account cannot blank");
                } else {
                    titleAccount.setError("");
                }

                if (password.getText().toString().equals("")) {
                    titlePassword.setError("Field Password cannot blank");
                } else {
                    titlePassword.setError("");
                }

                if (confirmPassword.getText().toString().equals("")) {
                    titleConfirmPassword.setError("Field ConfirmPassWord cannot blank");
                } else {
                    titleConfirmPassword.setError("");
                }

            } else {
                if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                    String acc = account.getText().toString();
                    if (userAccountDatabase.userAccountDAO().getByAccount(acc) == null) {
                        UserAccount userAccount = new UserAccount();
                        userAccount.setAccount(account.getText().toString());
                        userAccount.setPassword(password.getText().toString());
                        userAccount.setUserName(userName.getText().toString());
//                        if (userAccountDAO.insertAll(userAccount)) {
//                            Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(this, LoginActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show();
//                        }
                        try {
                            userAccountDatabase.userAccountDAO().insertAll(userAccount);
                            Toast.makeText(this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, LoginActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        titleAccount.setError("Account exist!");
                    }
                } else {
                    titleConfirmPassword.setError("Confirm password wrong");
                }
            }
        });
    }
}