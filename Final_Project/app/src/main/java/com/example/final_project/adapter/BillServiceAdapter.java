package com.example.final_project.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.dao.BillDAO;
import com.example.final_project.dao.BillServiceDAO;
import com.example.final_project.dao.CustomerDAO;
import com.example.final_project.dao.RoomDao;
import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.Bill;
import com.example.final_project.entity.Customer;
import com.example.final_project.entity.Room;
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
        if(typeServiceDAO.getID(String.valueOf(serviceBill.getService_id())) != null){
            ServiceType serviceType = typeServiceDAO.getID(String.valueOf(serviceBill.getService_id()));

            BillDAO billDAO = new BillDAO(context);
            RoomDao roomDao = new RoomDao(context);
            CustomerDAO customerDAO = new CustomerDAO(context);
            Bill bill = billDAO.getId(String.valueOf(serviceBill.getBill_id()));
            Room room = roomDao.getID(String.valueOf(bill.getRoomId()));
            Customer customer = customerDAO.getID(String.valueOf(bill.getCustomerId()));
            holder.room_required.setText("Room: "+ room.getName());
            holder.name_customer.setText("Customer Name: " + customer.getName());
            holder.service_date.setText("Time order service: "+ serviceBill.getService_date());
            holder.type_service_required.setText("Type Service: "+serviceType.getName());
            holder.quantity_service.setText("Service quantity: "+serviceBill.getService_quantity());
            holder.total_service_bill.setText("Total: "+serviceBill.getTotal()+" $");

            //Delete BillService
            holder.btn_delete_bill_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BillServiceDAO billServiceDAO = new BillServiceDAO(context);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle("Notification");
                    alertDialog.setIcon(R.mipmap.ic_launcher);
                    alertDialog.setMessage("Do you want to delete this bill?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(billServiceDAO.delete(serviceBill.getId()) > 0){
                                Toast.makeText(context, "Delete Sucessfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                            }

                            mServiceBill.clear();
                            mServiceBill.addAll(billServiceDAO.getAll());
                            notifyDataSetChanged();
                        }
                    });

                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();

                }
            });
        }

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
