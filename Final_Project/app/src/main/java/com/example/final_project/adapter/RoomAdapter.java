package com.example.final_project.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.dao.RoomDao;
import com.example.final_project.dao.RoomTypeClick;
import com.example.final_project.dao.RoomTypeDao;
import com.example.final_project.entity.Room;
import com.example.final_project.entity.RoomType;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.roomViewHolder> {

    private Context context;
    private List<Room> list;
    private RoomDao roomDao;
    private RoomTypeClick roomTypeClick;
    private TextInputEditText room_name,room_price;
    private RoomTypeDao roomTypeDao;
    private Spinner spn;


    SpinnerRoomType spinnerRoomType;
    Button btn_cancel,btn_add;
    List<RoomType> listRoomType;
    RoomType roomType;

    int roomType2;

    @NonNull
    @Override
    public roomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_format, parent, false);
        return new roomViewHolder(view);
    }

    public class roomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView item_phong_ten, item_phong_loaiphong, item_phong_giaphong;

        public roomViewHolder(@NonNull View itemView) {
            super(itemView);
            item_phong_ten = itemView.findViewById(R.id.room_name);
            item_phong_loaiphong = itemView.findViewById(R.id.room_type);
            item_phong_giaphong = itemView.findViewById(R.id.room_price);
            imageView = itemView.findViewById(R.id.id_delete);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull roomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Room room=list.get(position);
        holder.item_phong_ten.setText("Room: "+room.getName());

        roomTypeDao =new RoomTypeDao(context);
        listRoomType = roomTypeDao.getListRoomType();

        if (roomTypeDao.getID(String.valueOf(room.getRoomTypeId())) != null){
            try {
                roomType = roomTypeDao.getID(String.valueOf(room.getRoomTypeId()));
            }catch (Exception e){
                Log.d("zzzz", "onBindViewHolder phong adapter");
            }
            holder.item_phong_loaiphong.setText("Room Type: \n"+roomType.getName());
            holder.item_phong_giaphong.setText("Price: \n"+room.getPrice()+" VNĐ");
        }



///delete
        RoomDao dao = new RoomDao(context);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Are you sure to delete ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = dao.delete(list.get(holder.getAdapterPosition()).getId());
                        switch (check){
                            case  1 :
                                list.clear();
                                list.addAll(dao.getListRoom());
                                notifyDataSetChanged();
                                Toast.makeText(context,"Delete Sucsess",Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context,"Can't delete because have customer in bill",Toast.LENGTH_SHORT).show();
                                break;
                            case 0 :
                                Toast.makeText(context,"Delete Fail",Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }

                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
            }
        });





        holder.item_phong_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.add_room_dialog, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                room_name=dialog.findViewById(R.id.roomname);
                room_name.setText(room.getName());
                room_price=dialog.findViewById(R.id.room_price);
                room_price.setText(room.getPrice()+"");
                spn=dialog.findViewById(R.id.spn_item);

                btn_add=dialog.findViewById(R.id.btn_add);
                btn_cancel=dialog.findViewById(R.id.btn_cancel);

                spinnerRoomType = new SpinnerRoomType(context, (ArrayList<RoomType>) roomTypeDao.getListRoomType());
                spn.setAdapter(spinnerRoomType);
                for (int i=0;i<spn.getCount();i++){
                    listRoomType = roomTypeDao.getListRoomType();
                    if (list.get(position).getRoomTypeId() == listRoomType.get(i).getId()){
                        spn.setSelection(i);
                    }
                }

                spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        listRoomType = new ArrayList<>();
                        roomTypeDao = new RoomTypeDao(context);
                        listRoomType = roomTypeDao.getListRoomType();
                        roomType2 = listRoomType.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
///Update
                btn_add.setText("Update");
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (room_name.getText().length() == 0) {
                            Toast.makeText(context, "Must fill all", Toast.LENGTH_SHORT).show();
                        } else {

                            room.setName(room_name.getText().toString());
                            room.setRoomTypeId(roomType2);
                            room.setPrice(Integer.parseInt(room_price.getText().toString()));
                            room.setStatus(0);
                            if (dao.update(room) > 0) {
                                Toast.makeText(context, "Update Succsess", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(dao.getListRoom());
                                notifyDataSetChanged();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Update Fail", Toast.LENGTH_SHORT).show();
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
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public RoomAdapter(Context context, List<Room> list) {
        this.context = context;
        this.list = list;
        roomDao=new RoomDao(context);
    }

}