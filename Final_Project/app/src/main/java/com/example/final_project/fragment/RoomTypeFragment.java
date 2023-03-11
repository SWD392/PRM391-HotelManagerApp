package com.example.final_project.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.adapter.RoomTypeAdapter;
import com.example.final_project.dao.RoomTypeClick;
import com.example.final_project.dao.RoomTypeDao;
import com.example.final_project.entity.RoomType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;



import java.util.ArrayList;
import java.util.List;

public class RoomTypeFragment extends Fragment {
    private RecyclerView rcv_roomtype;
    FloatingActionButton flbutton;
    RoomTypeDao roomTypeDao;
    RoomTypeAdapter roomTypeAdapter;
    private List<RoomType> list=new ArrayList<>();
    TextInputLayout  text_input;
    Button btn_add;
    Button btn_cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.roomtype_fragment, container, false);
        rcv_roomtype = view.findViewById(R.id.rcv_roomtype);
        flbutton=view.findViewById(R.id.btn_add_typeRoom);
        roomTypeDao =new RoomTypeDao(getContext());
       list=roomTypeDao.getListRoomType();
       roomTypeAdapter = new RoomTypeAdapter(getContext(),list);
       flbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
opendialog();
           }
       });
       roomTypeAdapter.setRoomTypeClick(new RoomTypeClick() {
           @Override
           public void onClick(RoomType roomType) {
               AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
               View newView = LayoutInflater.from(getContext()).inflate(R.layout.roomtypedialog, null);
               builder.setView(newView);
               Dialog dialog = builder.create();
               Window window = dialog.getWindow();
               if(window==null){
                   return;
               }
               window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
               window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.show();
               text_input=newView.findViewById(R.id.textinput);
               btn_add=newView.findViewById(R.id.btn_add);
               btn_add.setText("Update");
               btn_cancel=newView.findViewById(R.id.btn_cancel);
               text_input.getEditText().setText(roomType.getName());

               //Update
               btn_add.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if (text_input.getEditText().getText().length() == 0) {
                           Toast.makeText(getContext(), "Must fill all blank", Toast.LENGTH_SHORT).show();
                       } else {
                           roomType.setName(text_input.getEditText().getText().toString());
                           if (roomTypeDao.updateRoomTypeName(roomType) > 0) {
                               Toast.makeText(getContext(), "Update Succsess", Toast.LENGTH_SHORT).show();
                               text_input.getEditText().setText("");
                               list.clear();
                               list.addAll(roomTypeDao.getListRoomType());
                               roomTypeAdapter.notifyDataSetChanged();
                               dialog.dismiss();
                           } else {
                               Toast.makeText(getContext(), "Update fail", Toast.LENGTH_SHORT).show();
                           }
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
       });
        rcv_roomtype.setLayoutManager(new LinearLayoutManager(getContext()));
        rcv_roomtype.setAdapter(roomTypeAdapter);
        return view;
    }

    private void opendialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.roomtypedialog, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        text_input=view.findViewById(R.id.textinput);
        btn_add=view.findViewById(R.id.btn_add);
        btn_cancel=view.findViewById(R.id.btn_cancel);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text_input.getEditText().getText().length() == 0) {
                    Toast.makeText(getContext(), "Must fill all blank", Toast.LENGTH_SHORT).show();
                } else {
                  RoomType  roomType = new RoomType();
                    roomType.setName(text_input.getEditText().getText().toString());
                    if (roomTypeDao.insertRoomType(roomType) > 0) {
                        Toast.makeText(getContext(), "Add Succsess", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(roomTypeDao.getListRoomType());
                        roomTypeAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Add Fail", Toast.LENGTH_SHORT).show();
                    }
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
