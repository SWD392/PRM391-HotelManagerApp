package com.example.final_project.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.final_project.R;
import com.example.final_project.adapter.RoomAdapter;
import com.example.final_project.adapter.RoomTypeAdapter;
import com.example.final_project.adapter.SpinnerRoomType;
import com.example.final_project.dao.RoomDao;
import com.example.final_project.dao.RoomTypeDao;
import com.example.final_project.entity.Room;
import com.example.final_project.entity.RoomType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class RoomListFragment extends Fragment {
    private RecyclerView rcv;
    FloatingActionButton fbutton;
    RoomAdapter roomAdapter;
    List<RoomType> listRoomType = new ArrayList<>();
    RoomTypeDao roomTypeDao;
    SpinnerRoomType spinnerRoomType;
    private Spinner spn_roomType;
    private Button btn_add, btn_cancel;
    private TextInputEditText room_name, room_price;
    RoomDao roomDao;
    List<String> error_noti;
    private List<Room> list = new ArrayList<>();
    int roomType;
    Room room;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.room_list_fragment, container, false);
        rcv = view.findViewById(R.id.rcv_roomList);
        fbutton = view.findViewById(R.id.btn_add);
        roomDao = new RoomDao(getContext());
        list = roomDao.getListRoom();
        roomAdapter = new RoomAdapter(getContext(), list);
        roomTypeDao = new RoomTypeDao(getActivity());

        fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listRoomType = roomTypeDao.getListRoomType();
                if (listRoomType.size() == 0) {
                    error_noti.add(" Room Type ");
                }
                if (error_noti.isEmpty()) {
                    opendialog();
                } else {
                    Toast.makeText(getActivity(), "You must add Room Type : " + error_noti, Toast.LENGTH_SHORT).show();
                    error_noti = new ArrayList<>();
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcv.setLayoutManager(gridLayoutManager);
        roomAdapter = new RoomAdapter(getContext(), list);
        rcv.setAdapter(roomAdapter);
        error_noti = new ArrayList<>();

        // Inflate the layout for this fragment
        return view;
    }


    private void opendialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_room_dialog, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        room_name = dialog.findViewById(R.id.roomname);
        room_price = dialog.findViewById(R.id.room_price);
        spn_roomType = dialog.findViewById(R.id.spn_roomType);
        btn_add = dialog.findViewById(R.id.btn_add);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        spinnerRoomType = new SpinnerRoomType(getContext(), (ArrayList<RoomType>) roomTypeDao.getListRoomType());
        spn_roomType.setAdapter(spinnerRoomType);
        spn_roomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listRoomType = roomTypeDao.getListRoomType();
                roomType = listRoomType.get(position).getId();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (room_name.getText().length() == 0) {
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    room = new Room();
                    room.setName(room_name.getText().toString());
                    room.setRoomTypeId(roomType);
                    room.setPrice(Integer.parseInt(room_price.getText().toString()));
                    room.setStatus(0);

//                    if (roomDao.insertRoom(room) > 0) {
                    roomDao.insert(room_name.getText().toString(), Integer.parseInt(room_price.getText().toString()), 1, roomType);
                    Toast.makeText(getContext(), "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(roomDao.getListRoom());
                    Log.d("Name", room_name.getText().toString());
                    Log.d("Type", String.valueOf(roomType));
                    Log.d("Price", room_price.getText().toString());
                    roomAdapter.notifyDataSetChanged();
                    dialog.dismiss();
//                    } else {
//                        Toast.makeText(getContext(), "Thêm phòng thất bại", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}