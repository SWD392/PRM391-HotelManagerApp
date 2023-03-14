package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.adapter.TypeServiceSpinnerAdapter;
import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class AddServiceBillActivity extends AppCompatActivity {


    TypeServiceSpinnerAdapter typeServiceSpinnerAdapter;

    ServiceType serviceType;

    Spinner service_type_spinner;
    TypeServiceDAO typeServiceDAO;

    List<ServiceType> listServiceType;

    EditText add_service_quantity;
    String serviceId, servicePrice;

    TextView add_service_total;

    ImageButton btn_refresh;
    int quantity, total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_bill);
        typeServiceDAO = new TypeServiceDAO(this);
        serviceType = new ServiceType();
        service_type_spinner = findViewById(R.id.service_type_spinner);

        //Spinner
        typeServiceSpinnerAdapter = new TypeServiceSpinnerAdapter(this, (ArrayList<ServiceType>) typeServiceDAO.getAll());
        service_type_spinner.setAdapter(typeServiceSpinnerAdapter);
        service_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listServiceType = typeServiceDAO.getAll();
                serviceId = String.valueOf(listServiceType.get(position).getId());
                serviceType = typeServiceDAO.getAll().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_refresh = findViewById(R.id.btn_refresh);

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicePrice = serviceType.getPrice();
                add_service_quantity = findViewById(R.id.add_service_quantity);
                add_service_total = findViewById(R.id.add_service_total);
                try {
                    quantity = Integer.parseInt(add_service_quantity.getText().toString());
                }catch (Exception e){
                    Log.d("zzzzz", "onItemSelected: so luong dv");
                    Toast.makeText(AddServiceBillActivity.this,"Nhập số lượng dịch vụ",Toast.LENGTH_LONG).show();
                }
                total = Integer.parseInt(servicePrice) * quantity;
                add_service_total.setText("Tổng tiền: "+total+" VNĐ");
            }
        });
    }
}