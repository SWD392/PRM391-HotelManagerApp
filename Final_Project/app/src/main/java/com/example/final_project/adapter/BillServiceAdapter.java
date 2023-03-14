package com.example.final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.dao.BillServiceDAO;
import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.ServiceBill;
import com.example.final_project.entity.ServiceType;

import java.util.List;

public class BillServiceAdapter extends RecyclerView.Adapter<BillServiceAdapter.BillServiceHolder>{

    private Context context;
    private List<ServiceBill> mServiceBill;

    public BillServiceAdapter(Context context, List<ServiceBill> mServiceBill) {
        this.context = context;
        this.mServiceBill = mServiceBill;
    }

    @NonNull
    @Override
    public BillServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_service, parent, false);
        return new BillServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillServiceHolder holder, int position) {
        ServiceBill serviceBill = mServiceBill.get(position);
        if(serviceBill == null){
            return;
        }

        TypeServiceDAO typeServiceDAO = new TypeServiceDAO(context);
        ServiceType serviceType = typeServiceDAO.getID(String.valueOf(serviceBill.getService_id()));

        BillServiceDAO billServiceDAO = new BillServiceDAO(context);


        holder.type_service_required.setText("Loại dịch vụ: "+serviceType.getName());
        holder.quantity_service.setText("Số lượng dịch vụ: "+serviceBill.getService_quantity());
        holder.total_service_bill.setText("Tổng tiền: "+serviceBill.getTotal()+" VNĐ");

    }

    @Override
    public int getItemCount() {
        if(mServiceBill != null){
            return mServiceBill.size();
        }
        return 0;
    }

    public class BillServiceHolder extends RecyclerView.ViewHolder{

        private TextView service_date;
        private TextView room_required;
        private TextView name_customer;
        private TextView type_service_required, quantity_service, total_service_bill;
        private ImageView btn_delete_bill_service;

        public BillServiceHolder(@NonNull View itemView) {
            super(itemView);

            service_date = itemView.findViewById(R.id.service_date);
            room_required = itemView.findViewById(R.id.room_require);
            name_customer = itemView.findViewById(R.id.name_customer);
            type_service_required = itemView.findViewById(R.id.type_service_required);
            quantity_service = itemView.findViewById(R.id.quantity_service);
            total_service_bill = itemView.findViewById(R.id.total_service_bill);
            btn_delete_bill_service = itemView.findViewById(R.id.btn_delete_billservice);
        }
    }
}
