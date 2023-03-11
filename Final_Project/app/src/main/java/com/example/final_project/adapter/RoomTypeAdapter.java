package com.example.final_project.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.dao.RoomTypeClick;
import com.example.final_project.dao.RoomTypeDao;
import com.example.final_project.entity.RoomType;

import java.util.List;

public class RoomTypeAdapter extends RecyclerView.Adapter<RoomTypeAdapter.viewholder> {
private Context context;
    private List<RoomType> list;
    private RoomTypeDao roomTypeDao;
    private RoomTypeClick roomTypeClick;

    public class viewholder  extends RecyclerView.ViewHolder{
        TextView tv_namelp;
        ImageView btn_delete;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_namelp=itemView.findViewById(R.id.tv_namelp);
            btn_delete=itemView.findViewById(R.id.btn_delete);
        }
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_roomtype,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        RoomType roomType=list.get(position);
        holder.tv_namelp.setText(""+roomType.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomTypeClick.onClick(list.get(position));
            }
        });
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setTitle("Are You sure want to delete ???")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int check = roomTypeDao.deleteRoomType(list.get(holder.getAdapterPosition()).getId());
                                switch (check){
                                    case  1 :
                                        list.clear();
                                        list.addAll(roomTypeDao.getListRoomType());
                                        notifyDataSetChanged();
                                        Toast.makeText(context,"Delete Success",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        Toast.makeText(context,"Can't delete because have room use this type",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0 :
                                        Toast.makeText(context,"Delete Fail",Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        break;
                                }

                            }
                        })
                        .setNegativeButton("CANNEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public RoomTypeAdapter(Context context, List<RoomType> list) {
        this.context = context;
        this.list = list;
        roomTypeDao=new RoomTypeDao(context);
    }



    @Override
    public int getItemCount() {
        return list.size() ;
    }
public void setRoomTypeClick(RoomTypeClick roomTypeClick){
        this.roomTypeClick= roomTypeClick;
}

}
