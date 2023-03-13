package com.example.final_project.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project.R;
import com.example.final_project.adapter.TypeServiceAdapter;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class TypeServiceFragment extends Fragment {

    private RecyclerView rcvTypeService;
    private TypeServiceAdapter typeServiceAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type_service, container, false);
        rcvTypeService = view.findViewById(R.id.rcv_type_service);

        List<ServiceType> list = new ArrayList<>();
        list.add(new ServiceType(1, "Service 1", 1000));
        list.add(new ServiceType(2, "Service 2", 1000));
        list.add(new ServiceType(3, "Service 3", 1000));
        typeServiceAdapter = new TypeServiceAdapter(getContext(), list);
        typeServiceAdapter.notifyDataSetChanged();
        rcvTypeService.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvTypeService.setAdapter(typeServiceAdapter);
        return view;

    }
}