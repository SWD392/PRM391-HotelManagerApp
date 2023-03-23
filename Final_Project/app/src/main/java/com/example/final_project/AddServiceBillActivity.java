package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.adapter.BillServiceSpinnerApdater;
import com.example.final_project.adapter.TypeServiceSpinnerAdapter;
import com.example.final_project.dao.BillDAO;
import com.example.final_project.dao.BillServiceDAO;
import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.Bill;
import com.example.final_project.entity.RoomStatus;
import com.example.final_project.entity.ServiceBill;
import com.example.final_project.entity.ServiceType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddServiceBillActivity extends AppCompatActivity {


    TypeServiceSpinnerAdapter typeServiceSpinnerAdapter;

    BillServiceSpinnerApdater billServiceSpinnerApdater;
    ServiceType serviceType;
    Bill bill;
    SimpleDateFormat sdf;
    Spinner service_type_spinner, bill_service_spinner;
    TypeServiceDAO typeServiceDAO;

    BillServiceDAO billServiceDAO;
    BillDAO billDAO;
    List<ServiceType> listServiceType;

    List<Bill> listBill;

    EditText add_service_quantity;
    String serviceId, servicePrice, billId;

    TextView add_service_total, add_service_date;

    ImageButton btn_refresh;
    int quantity, total;

    Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_bill);
        typeServiceDAO = new TypeServiceDAO(this);
        billDAO = new BillDAO(this);
        billServiceDAO = new BillServiceDAO(this);
        serviceType = new ServiceType();
        bill = new Bill();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        service_type_spinner = findViewById(R.id.service_type_spinner);
        bill_service_spinner = findViewById(R.id.spn_bill_service);
        add_service_quantity = findViewById(R.id.add_service_quantity);
        add_service_date = findViewById(R.id.add_service_date);
        add_service_total = findViewById(R.id.add_service_total);
        String datetime = sdf.format(c.getTime());
        add_service_date.setText("Ngày tạo hóa đơn: "+datetime);
        add_service_quantity.setText("1");
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


        billServiceSpinnerApdater = new BillServiceSpinnerApdater(this, (ArrayList<Bill>) billDAO.getAll());
        bill_service_spinner.setAdapter(billServiceSpinnerApdater);
        bill_service_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listBill = getAllstatus0();
                billId = String.valueOf(listBill.get(position).getId());
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

        Button btn_add_bill_service = findViewById(R.id.btn_add_bill_serv);

        btn_add_bill_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceBill serviceBill = new ServiceBill();

                serviceBill.setBill_id(Integer.parseInt(billId));
                serviceBill.setService_id(Integer.parseInt(serviceId));
                serviceBill.setService_date(datetime);
                serviceBill.setService_quantity(quantity);
                serviceBill.setTotal(total);
                billServiceDAO.insert(serviceBill);
                Toast.makeText(AddServiceBillActivity.this, "Add Service Bill Succesfully", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<Bill> getAllstatus0() {
        List<Bill> bills = new ArrayList<>();
        bills.add(new Bill(1, 1, 1, 1, "15/03/2023","16/03/2023", 1, "aaa", "15/03/2023", 50,50, 100));
        bills.add(new Bill(2, 1, 1, 1, "15/03/2023","16/03/2023", 1, "aaa", "15/03/2023", 50,50, 100));
        bills.add(new Bill(3, 1, 1, 1, "15/03/2023","16/03/2023", 1, "aaa", "15/03/2023", 50,50, 100));
        return bills;
    }
}