package com.example.final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.final_project.R;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;

public class TypeServiceSpinnerAdapter extends ArrayAdapter<ServiceType> {
    private Context context;
    private ArrayList<ServiceType> objects;

    TextView tvspntendv;

    public TypeServiceSpinnerAdapter(Context context, ArrayList<ServiceType> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.service_type_spinner, null);

        }
        final ServiceType obj = objects.get(position);
        if (obj != null) {
            tvspntendv = holder.findViewById(R.id.item_spn_service_type);
            tvspntendv.setText(obj.getName());
        }
        return holder;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.service_type_spinner,null);

        }
        final ServiceType obj = objects.get(position);
        if (obj != null){
            tvspntendv = holder.findViewById(R.id.item_spn_service_type);
            tvspntendv.setText(obj.getName());
        }
        return holder;
    }
}
