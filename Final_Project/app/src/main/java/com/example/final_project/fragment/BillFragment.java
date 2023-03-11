package com.example.final_project.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.final_project.R;
import com.example.final_project.adapter.BillAdapter;
import com.example.final_project.adapter.CustomerSpinnerAdapter;
import com.example.final_project.adapter.RoomSpinnerAdapter;
import com.example.final_project.adapter.StatusSpinnerAdapter;
import com.example.final_project.entity.Bill;
import com.example.final_project.entity.Customer;
import com.example.final_project.entity.Room;
import com.example.final_project.entity.RoomStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class BillFragment extends Fragment {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private StatusSpinnerAdapter statusAdapter;

    private CustomerSpinnerAdapter customerAdapter;

    private RoomSpinnerAdapter roomAdapter;

    private int year, month, day;


    ImageView startDateBtn;
    TextView startDateTv;
    ImageView endDateBtn;
    TextView endDateTv;
    Spinner spnCustomer;
    Spinner spnRoom;
    Spinner spnService;
    CheckBox cbCheckout;
    TextView tvNote;
    TextView totalBill;
    Button findRoom;
    ImageView refresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bill_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rcv_bill);
        BillAdapter billAdapter = new BillAdapter(getBillList(),getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(billAdapter);

        Spinner spnRoomStatus = view.findViewById(R.id.spinner_status_hd);
        statusAdapter = new StatusSpinnerAdapter(view.getContext(), R.layout.item_status_spinner_selected, getListStatus());
        spnRoomStatus.setAdapter(statusAdapter);



        spnRoomStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //todo: filter theo từng status ở đây
                Toast.makeText(view.getContext(), statusAdapter.getItem(position).statusName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageView addViewBtn = view.findViewById(R.id.add_bill_btn);
        addViewBtn.setOnClickListener(button -> {
            openAddBillDialog(view);
        });

    }

    private void openAddBillDialog(View view) {
        Dialog dialog = new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bill_dialog);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winLayoutParams = window.getAttributes();
        winLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(winLayoutParams);

        Button addBill = dialog.findViewById(R.id.add_bill);
        Button cancel = dialog.findViewById(R.id.cancel);

        startDateBtn = dialog.findViewById(R.id.start_date_btn);
        startDateTv = dialog.findViewById(R.id.start_date_tv);
        endDateBtn = dialog.findViewById(R.id.end_date_btn);
        endDateTv = dialog.findViewById(R.id.end_date_tv);
        spnCustomer = dialog.findViewById(R.id.spinner_customer);
        spnRoom = dialog.findViewById(R.id.spinner_room);
        spnService = dialog.findViewById(R.id.spinner_service);
        cbCheckout = dialog.findViewById(R.id.checkbox_checkout);
        tvNote = dialog.findViewById(R.id.bill_note);
        totalBill = dialog.findViewById(R.id.total_bill);

        findRoom = dialog.findViewById(R.id.find_room_btn);
        refresh = dialog.findViewById(R.id.refresh_btn);

        //Date execute
        DatePickerDialog.OnDateSetListener fromDate = (datePicker, year, month, dayOfMonth) -> {
            this.year = year;
            this.month = month;
            this.day = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(this.year, this.month, this.day);
            startDateTv.setText(dateFormat.format(c.getTime()));
        };

        DatePickerDialog.OnDateSetListener endDate = (datePicker, year, month, dayOfMonth) -> {
            this.year = year;
            this.month = month;
            this.day = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(this.year, this.month, this.day);
            endDateTv.setText(dateFormat.format(c.getTime()));
        };

        startDateBtn.setOnClickListener(button -> {
            Calendar nowTime = Calendar.getInstance();
            year = nowTime.get(Calendar.YEAR);
            month = nowTime.get(Calendar.MONTH);
            day = nowTime.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), 0, fromDate, year, month, day);
            datePicker.show();
        });
        endDateBtn.setOnClickListener(button -> {
            Calendar nowTime = Calendar.getInstance();
            year = nowTime.get(Calendar.YEAR);
            month = nowTime.get(Calendar.MONTH);
            day = nowTime.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), 0, endDate, year, month, day);
            datePicker.show();
        });

        //List spinner customer execute
        customerAdapter = new CustomerSpinnerAdapter(view.getContext(), R.layout.item_customer_spinner, getListCustomer());
        spnCustomer.setAdapter(customerAdapter);

        //List room spinner execute
        //todo: tìm phòng theo ngày, sau khi find theo ngày thì hiện ra spinner phòng
        roomAdapter = new RoomSpinnerAdapter(view.getContext(), R.layout.item_room_spinner, getRooms());
        spnRoom.setAdapter(roomAdapter);

        dialog.show();

    }

    private List<Bill> getBillList() {
        List<Bill> billList = new ArrayList<>();
        billList.add(new Bill(1, 1, 1, 1, "12/3/2023", "13/3/2023", 1, "", "12/3/2023", 1000, 1000, 1000));
        billList.add(new Bill(2, 1, 3, 14, "12/3/2023", "13/3/2023", 0, "", "12/3/2023", 1000, 1000, 1000));
        billList.add(new Bill(3, 1, 2, 15, "12/3/2023", "15/3/2023", 2, "", "12/3/2023", 1000, 1000, 1000));
        return billList;
    }

    private List<RoomStatus> getListStatus() {
        //todo: Trả về list các status có trong db (tạm thời fix cứng data)
        List<RoomStatus> roomStatuses = new ArrayList<>();
        roomStatuses.add(new RoomStatus(1, "Chua tra"));
        roomStatuses.add(new RoomStatus(2, "Da tra"));
        roomStatuses.add(new RoomStatus(3, "Chua nhan phong"));
        return roomStatuses;
    }

    private List<Customer> getListCustomer() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1, "A", "0123", Calendar.getInstance().toString()));
        customers.add(new Customer(2, "B", "01234", Calendar.getInstance().toString()));
        customers.add(new Customer(3, "C", "01235", Calendar.getInstance().toString()));
        customers.add(new Customer(4, "D", "01236", Calendar.getInstance().toString()));
        return customers;
    }

    private List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, "1", 1, 100, 1));
        rooms.add(new Room(2, "2", 2, 100, 1));
        rooms.add(new Room(3, "3", 3, 100, 1));
        rooms.add(new Room(4, "4", 1, 100, 1));
        return rooms;
    }
}