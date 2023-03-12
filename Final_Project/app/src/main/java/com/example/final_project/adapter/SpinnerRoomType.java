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
import com.example.final_project.entity.RoomType;

import java.util.ArrayList;

public class SpinnerRoomType extends ArrayAdapter<RoomType> {
    private Context context;
    private ArrayList<RoomType> objects;
    TextView tvspnloaiphong;

    public SpinnerRoomType( Context context, ArrayList<RoomType> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.spinner_layout_item_roomtype,null);

        }
        final RoomType obj = objects.get(position);
        if (obj != null){

            tvspnloaiphong = holder.findViewById(R.id.spn_item);
            tvspnloaiphong.setText(obj.getName());
        }
        return holder;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View holder = convertView;
        if (holder==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            holder = inflater.inflate(R.layout.spinner_layout_item_roomtype,null);

        }
        final RoomType obj = objects.get(position);
        if (obj != null){

            tvspnloaiphong = holder.findViewById(R.id.spn_item);
            tvspnloaiphong.setText(obj.getName());
        }
        return holder;
    }
}
