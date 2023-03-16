package com.example.final_project.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.dao.BillDAO;
import com.example.final_project.dao.RoomDao;
import com.example.final_project.dao.RoomTypeDao;
import com.example.final_project.entity.Bill;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private List<Bill> billList;

    private Context context;
    BillDAO billDAO;

    RoomDao roomDao;
    public BillAdapter(List<Bill> billList, Context context) {
        this.billList = billList;
        this.context = context;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context1 = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context1);
        View view = inflater.inflate(R.layout.item_bill_detail, parent, false);
        return new BillViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = billList.get(position);
        billDAO = new BillDAO(context);
        roomDao = new RoomDao(context);
        holder.roomName.setText(roomDao.getRoomById(bill.getRoomId()).get(0).getName());
        holder.customerName.setText(bill.getCustomerId() + "");
        holder.fromDate.setText(bill.getFromDate());
        holder.endDate.setText(bill.getEndDate());
        holder.totalBill.setText(bill.getBillTotal() + "");
        holder.bill = bill;

        switch (bill.getStatus()) {
            case 1:
                //not check-in
                break;
            case 2:
                //check-in, not check-out
                holder.billStatus.setVisibility(View.VISIBLE);
                holder.billStatus.setText("CHECK IN");
                holder.billStatus.setTextColor(Color.RED);
                holder.checkInBtn.setText("Check out");
                holder.cancelBillButton.setVisibility(View.GONE);
                break;
            case 3:
            default:
                holder.billStatus.setVisibility(View.VISIBLE);
                holder.billStatus.setText("CHECK OUT");
                holder.billStatus.setTextColor(Color.GREEN);
                holder.checkInBtn.setVisibility(View.GONE);
                holder.cancelBillButton.setVisibility(View.GONE);

        }

        holder.viewDetail.setOnClickListener(button -> {
            openDialogDetail(context, bill);
        });

        holder.cancelBillButton.setOnClickListener(button -> {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.confirm_bill_dialog);

            Button confirmBtn = dialog.findViewById(R.id.confirm_btn);
            Button cancel = dialog.findViewById(R.id.cancel_btn);

            confirmBtn.setOnClickListener(cfButton -> {
                if (billDAO.deleteBill(bill.getId())) {
                    Toast.makeText(context, "Bill was deleted", Toast.LENGTH_SHORT).show();
                    billList.clear();
                    billList = billDAO.getAll()
                            .stream()
                            .filter(billEntity -> billEntity.getStatus() == 1)
                            .collect(Collectors.toList());
                    notifyDataSetChanged();
                    dialog.cancel();
                } else {
                    Toast.makeText(context, "Cannot delete bill", Toast.LENGTH_SHORT).show();
                }
            });
            cancel.setOnClickListener(cancelBtn -> {
                dialog.cancel();
            });
            dialog.show();
        });

        holder.checkInBtn.setOnClickListener(button -> {
            Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.confirm_bill_dialog);

            Button confirmBtn = dialog.findViewById(R.id.confirm_btn);
            Button cancel = dialog.findViewById(R.id.cancel_btn);

            confirmBtn.setOnClickListener(cfButton -> {
                if (bill.getStatus() == 1) {
                    bill.setStatus(2);
                    if (billDAO.update(bill)) {
                        Toast.makeText(context, "Check in success", Toast.LENGTH_SHORT).show();
                        billList.clear();
                        billList = billDAO.getAll()
                                .stream()
                                .filter(billEntity -> billEntity.getStatus() == 1)
                                .collect(Collectors.toList());
                        notifyDataSetChanged();
                        dialog.cancel();
                    } else {
                        Toast.makeText(context, "Cannot Check in", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    bill.setStatus(3);
                    if (billDAO.update(bill)) {
                        Toast.makeText(context, "Check out success", Toast.LENGTH_SHORT).show();
                        billList.clear();
                        billList = billDAO.getAll()
                                .stream()
                                .filter(billEntity -> billEntity.getStatus() == 2)
                                .collect(Collectors.toList());
                        notifyDataSetChanged();
                        dialog.cancel();

                    } else {
                        Toast.makeText(context, "Cannot Check out", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancel.setOnClickListener(cancelBtn -> {
                dialog.cancel();
            });
            dialog.show();
        });

    }

    private TextView startDateDetail;
    private TextView endDateDetail;
    private TextView customerDetail;
    private TextView roomDetail;
    private TextView statusDetail;
    private TextView noteDetail;
    private TextView totalBillDetail;
    private Button doneViewBtn;

    private void openDialogDetail(Context context, Bill bill) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bill_dialog_view_detail);

        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winLayoutParams = window.getAttributes();
        winLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(winLayoutParams);

        startDateDetail = dialog.findViewById(R.id.start_date_detail);
        endDateDetail = dialog.findViewById(R.id.end_date_detail);
        customerDetail = dialog.findViewById(R.id.customer_name_detail);
        roomDetail = dialog.findViewById(R.id.room_detail);
        noteDetail = dialog.findViewById(R.id.bill_note_detail);
        statusDetail = dialog.findViewById(R.id.status_detail);
        totalBillDetail = dialog.findViewById(R.id.total_bill_detail);
        doneViewBtn = dialog.findViewById(R.id.done_btn);

        startDateDetail.setText(bill.getFromDate());
        endDateDetail.setText(bill.getEndDate());
        customerDetail.setText(bill.getCustomerId() + "");
        roomDetail.setText(roomDao.getRoomById(bill.getRoomId()).get(0).getName());
        noteDetail.setText(bill.getNote());
        //todo: Sửa thành status
        statusDetail.setText(getStatus(bill.getStatus() ));
        totalBillDetail.setText(bill.getBillTotal() + "");
        doneViewBtn.setOnClickListener(button -> {
            dialog.cancel();
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    private String getStatus(int statusId) {
        switch (statusId) {
            case 1:
                return "NOT CHECK IN";
            case 2:
                return "CHECKED IN";
            case 3:
            default:
                return "CHECKED OUT";
        }
    }

    public class BillViewHolder extends RecyclerView.ViewHolder {

        private Bill bill;
        private TextView roomName;
        private TextView customerName;
        private TextView fromDate;
        private TextView endDate;
        private TextView totalBill;
        private Button checkInBtn;
        private Button viewDetail;

        private Button cancelBillButton;

        private TextView billStatus;

        public BillViewHolder(@NonNull View view) {
            super(view);
            roomName = view.findViewById(R.id.room_name);
            customerName = view.findViewById(R.id.customer_name);
            fromDate = view.findViewById(R.id.from_date);
            endDate = view.findViewById(R.id.to_date);
            totalBill = view.findViewById(R.id.total_bill);
            checkInBtn = view.findViewById(R.id.check_in_btn);
            viewDetail = view.findViewById(R.id.view_detail_bill_btn);
            billStatus = view.findViewById(R.id.tv_bill_status);
            cancelBillButton = view.findViewById(R.id.cancel_bill_btn);
        }
    }
}
