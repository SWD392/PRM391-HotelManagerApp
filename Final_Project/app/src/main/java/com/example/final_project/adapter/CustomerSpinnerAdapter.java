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
import com.example.final_project.entity.Customer;

import java.util.List;

public class CustomerSpinnerAdapter extends ArrayAdapter<Customer> {

    public CustomerSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_selected, parent, false);
        TextView tvCustomer = convertView.findViewById(R.id.customer_selected);

        Customer customer = this.getItem(position);
        if (customer != null) {
            tvCustomer.setText(customer.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_spinner, parent, false);
        TextView tvCustomer = convertView.findViewById(R.id.customer_spinner);

        Customer customer = this.getItem(position);
        if (customer != null) {
            tvCustomer.setText(customer.getName());
        }
        return convertView;
    }
}
