package com.example.final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.entity.ServiceType;

import java.util.List;

public class TypeServiceAdapter extends RecyclerView.Adapter<TypeServiceAdapter.TypeServiceViewHolder>{

    private Context context;
    private List<ServiceType> mListServiceType;


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
        holder.priceService.setText(Integer.toString(serviceType.getPrice()));

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

        public TypeServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            nameService = itemView.findViewById(R.id.tv_name_service);
            priceService = itemView.findViewById(R.id.tv_price_service);

        }


    }

}
