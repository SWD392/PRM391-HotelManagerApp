package com.example.final_project.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.final_project.AddServiceActivity;
import com.example.final_project.R;
import com.example.final_project.adapter.TypeServiceAdapter;
import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;
import java.util.List;


public class TypeServiceFragment extends Fragment {

    private RecyclerView rcvTypeService;
    private TypeServiceAdapter typeServiceAdapter;

    private Button addServicebtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type_service, container, false);
        rcvTypeService = view.findViewById(R.id.rcv_type_service);
        TypeServiceDAO typeServiceDAO = new TypeServiceDAO(getContext());
        List<ServiceType> list = new ArrayList<>();
        list.addAll(typeServiceDAO.getAll());
        typeServiceAdapter = new TypeServiceAdapter(getContext(), list);
        typeServiceAdapter.notifyDataSetChanged();
        rcvTypeService.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTypeService.setAdapter(typeServiceAdapter);
        addServicebtn = view.findViewById(R.id.btn_add_type_service);
        addServicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddServiceActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}