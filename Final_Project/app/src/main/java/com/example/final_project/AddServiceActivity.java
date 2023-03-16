package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.ServiceType;

public class AddServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        TextView nameService = findViewById(R.id.add_name_service);
        TextView priceService = findViewById(R.id.add_price_service);
        Button addBtn = findViewById(R.id.add_button);
        TypeServiceDAO typeServiceDAO = new TypeServiceDAO(this);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameService.getText().length() == 0 || priceService.getText().length() == 0) {
                    Toast.makeText(AddServiceActivity.this, "Not blank", Toast.LENGTH_SHORT).show();
                }else {
                    ServiceType serviceType = new ServiceType();
                    serviceType.setName(nameService.getText().toString());
                    serviceType.setPrice(priceService.getText().toString());
                    if (typeServiceDAO.insert(serviceType) > 0) {
                        Toast.makeText(AddServiceActivity.this, "Add Service Succesfully", Toast.LENGTH_SHORT).show();
                        nameService.setText("");
                        priceService.setText("");
                    } else {
                        Toast.makeText(AddServiceActivity.this, "Add Service Failed", Toast.LENGTH_SHORT).show();
                    }
            }
        }});
    }
}