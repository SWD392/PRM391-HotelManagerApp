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
import com.example.final_project.dao.CustomerDAO;
import com.example.final_project.dao.RoomDao;
import com.example.final_project.entity.Bill;

import java.util.ArrayList;

public class BillServiceSpinnerApdater extends ArrayAdapter<Bill> {
    private Context context;
    private ArrayList<Bill> objects;


    TextView tvspnroomname,tvspncustomername,tvfrom,tvto;
    public BillServiceSpinnerApdater(@NonNull Context context, ArrayList<Bill> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.bill_service_spinner,null);

        }
        final Bill obj = objects.get(position);
        if (obj != null){
            tvspnroomname = holder.findViewById(R.id.item_spn_room_name);
            tvspncustomername = holder.findViewById(R.id.item_spn_customer_name);
            tvfrom = holder.findViewById(R.id.item_spn_from);
            tvto = holder.findViewById(R.id.item_spn_to);

            tvfrom.setText("Từ "+obj.getFromDate());
            tvto.setText("đến "+obj.getEndDate());

            RoomDao roomDao = new RoomDao(context);
            tvspnroomname.setText("Phòng: "+roomDao.getID(String.valueOf(obj.getRoomId())).getName());

            CustomerDAO customerDAO = new CustomerDAO(context);
            tvspncustomername.setText("KH: "+customerDAO.getID(String.valueOf(obj.getCustomerId())).getName());


        }
        return holder;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.bill_service_spinner,null);

        }
        final Bill obj = objects.get(position);
        if (obj != null){
            tvspnroomname = holder.findViewById(R.id.item_spn_room_name);
            tvspncustomername = holder.findViewById(R.id.item_spn_customer_name);
            tvfrom = holder.findViewById(R.id.item_spn_from);
            tvto = holder.findViewById(R.id.item_spn_to);

            tvfrom.setText("Từ "+obj.getFromDate());
            tvto.setText("đến "+obj.getEndDate());

            RoomDao roomDao = new RoomDao(context);
            tvspnroomname.setText("Phòng: "+roomDao.getID(String.valueOf(obj.getRoomId())).getName());

            CustomerDAO customerDAO = new CustomerDAO(context);
            tvspncustomername.setText("KH: "+customerDAO.getID(String.valueOf(obj.getCustomerId())).getName());


        }
        return holder;
    }
}
