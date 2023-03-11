package com.example.final_project.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.UpdateServActivity;
import com.example.final_project.dao.TypeServiceDAO;
import com.example.final_project.entity.ServiceType;

import java.util.ArrayList;
import java.util.List;

public class TypeServiceAdapter extends RecyclerView.Adapter<TypeServiceAdapter.TypeServiceViewHolder>{

    private Context context;
    private List<ServiceType> mListServiceType;

    private Activity activity;
    private TypeServiceDAO typeServiceDAO;
    public TypeServiceAdapter(Context context, List<ServiceType> mListServiceType) {
        this.context = context;
        this.mListServiceType = mListServiceType;
    }


    @NonNull
    @Override
    public TypeServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type_service, parent, false);
        return new TypeServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeServiceViewHolder holder, int position) {
        ServiceType serviceType = mListServiceType.get(position);
        if(serviceType == null){
            return;
        }

        holder.nameService.setText(serviceType.getName());
        holder.priceService.setText(serviceType.getPrice());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeServiceDAO = new TypeServiceDAO(context);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Notification");
                alertDialog.setIcon(R.mipmap.ic_launcher);
                alertDialog.setMessage("Do you want to delete this service?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(typeServiceDAO.delete(serviceType.getId()) > 0){
                            Toast.makeText(context, "Delete Sucessfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
                        }

                        mListServiceType.clear();
                        mListServiceType.addAll(typeServiceDAO.getAll());
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

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateServActivity.class);

                intent.putExtra("name", serviceType.getName());
                intent.putExtra("price",serviceType.getPrice());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListServiceType != null){
            return mListServiceType.size();
        }
        return 0;
    }

    public class TypeServiceViewHolder extends RecyclerView.ViewHolder {

        private TextView nameService;
        private TextView priceService;

        LinearLayout btn_edit;
        private ImageView btn_delete;
        public TypeServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            nameService = itemView.findViewById(R.id.tv_name_service);
            priceService = itemView.findViewById(R.id.tv_price_service);
            btn_delete = itemView.findViewById(R.id.item_dich_vu_rcy_btn_delete);
            btn_edit = itemView.findViewById(R.id.item_service_edit);
        }


    }

}
