package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.ServiceType;

public class UpdateServActivity extends AppCompatActivity {

    String name_service,price_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_serv);

        EditText edit_name_service = findViewById(R.id.update_name_service);
        EditText edit_price_service = findViewById(R.id.update_price_service);
        Button update_button = findViewById(R.id.update_button);
        name_service = getIntent().getStringExtra("name");
        price_service = getIntent().getStringExtra("price");
        edit_name_service.setText(name_service);
        edit_price_service.setText(price_service);
        TypeServiceDAO typeServiceDAO = new TypeServiceDAO(UpdateServActivity.this);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ServiceType serviceType = new ServiceType();
                    serviceType.setName(edit_name_service.getText().toString());
                    serviceType.setPrice(edit_price_service.getText().toString());
                    if (typeServiceDAO.update(serviceType) > 0){
                        Toast.makeText(UpdateServActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateServActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}