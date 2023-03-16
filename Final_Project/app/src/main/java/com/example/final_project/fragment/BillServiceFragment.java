package com.example.final_project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.final_project.AddServiceBillActivity;
import com.example.final_project.R;
import com.example.final_project.adapter.BillServiceAdapter;
import com.example.final_project.adapter.TypeServiceAdapter;
import com.example.final_project.adapter.TypeServiceSpinnerAdapter;
import com.example.final_project.dao.BillServiceDAO;
import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.ServiceBill;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;
import java.util.List;


public class BillServiceFragment extends Fragment {

    private RecyclerView rcvBillService;

    private BillServiceAdapter billServiceAdapter;



    private Button addBillServicebtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_service, container, false);
        rcvBillService = view.findViewById(R.id.rcv_bill_service);
        BillServiceDAO billServiceDAO = new BillServiceDAO(getContext());
        List<ServiceBill> list = new ArrayList<>();
//        list.add(new ServiceBill(1 , 1, 23000));
//        list.add(new ServiceBill(2 , 2, 13000));
//        list.add(new ServiceBill(1 , 2, 42000));
        list.addAll(billServiceDAO.getAll());
        billServiceAdapter = new BillServiceAdapter(getContext(), list);
        billServiceAdapter.notifyDataSetChanged();
        rcvBillService.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvBillService.setAdapter(billServiceAdapter);

        addBillServicebtn = view.findViewById(R.id.btn_add_bill_service);

        addBillServicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddServiceBillActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}