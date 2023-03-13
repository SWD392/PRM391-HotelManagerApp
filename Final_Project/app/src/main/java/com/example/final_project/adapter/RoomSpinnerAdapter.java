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
import com.example.final_project.entity.Room;

import java.util.List;

public class RoomSpinnerAdapter extends ArrayAdapter<Room> {
    public RoomSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Room> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_selected, parent, false);
        TextView tvRoom = convertView.findViewById(R.id.room_selected);
        TextView tvRoomType = convertView.findViewById(R.id.room_type_selected);

        Room room = this.getItem(position);
        if (room != null) {
            tvRoom.setText("Room: " + room.getName());
            //todo: tìm tên room type, tạm thời chỉ set type_id
            tvRoomType.setText("Type: " + room.getRoomTypeId());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_spinner, parent, false);
        TextView tvRoom = convertView.findViewById(R.id.room_spinner);
        TextView tvRoomType = convertView.findViewById(R.id.room_type_spinner);

        Room room = this.getItem(position);
        if (room != null) {
            tvRoom.setText("Room: " + room.getName());
            tvRoomType.setText("Type: " + room.getRoomTypeId());

        }
        return convertView;
    }
}
