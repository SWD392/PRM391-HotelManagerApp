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
import com.example.final_project.dao.BillDAO;
import com.example.final_project.entity.Bill;
import com.example.final_project.entity.Customer;
import com.example.final_project.entity.Room;
import com.example.final_project.entity.RoomStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("SimpleDateFormat")
public class BillFragment extends Fragment {

    private BillDAO billDao;

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
    private int customerId;

    private int roomId;

    private String billStartDate;

    private String billEndDate;

    private int billTotal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bill_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        billDao = new BillDAO(view.getContext());
        super.onViewCreated(view, savedInstanceState);

        getBillRecyclerView(view);

        ImageView addViewBtn = view.findViewById(R.id.add_bill_btn);
        addViewBtn.setOnClickListener(button -> {
            openAddBillDialog(view);
        });

    }


    private void openAddBillDialog(View view) {
        billDao = new BillDAO(view.getContext());
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

        Button addBillBtn = dialog.findViewById(R.id.add_bill);
        Button cancelBtn = dialog.findViewById(R.id.cancel);

        startDateBtn = dialog.findViewById(R.id.start_date_btn);
        startDateTv = dialog.findViewById(R.id.start_date_tv);
        endDateBtn = dialog.findViewById(R.id.end_date_btn);
        endDateTv = dialog.findViewById(R.id.end_date_tv);
        spnCustomer = dialog.findViewById(R.id.spinner_customer);
        spnRoom = dialog.findViewById(R.id.spinner_room);
        cbCheckout = dialog.findViewById(R.id.checkbox_checkout);
        tvNote = dialog.findViewById(R.id.bill_note);
        totalBill = dialog.findViewById(R.id.total_bill);

        View roomLayout = dialog.findViewById(R.id.linear_room);
        findRoom = dialog.findViewById(R.id.find_room_btn);

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

        findRoom.setOnClickListener(button -> {
            if (startDateTv.getText().toString().equals("") || endDateTv.getText().toString().equals("")) {
                Toast.makeText(view.getContext(), "You must pick a date", Toast.LENGTH_SHORT).show();
            } else {
                GregorianCalendar start = convertStringToDate(startDateTv.getText().toString());
                GregorianCalendar end = convertStringToDate(endDateTv.getText().toString());

                if (start.compareTo(end) > 0) {
                    Toast.makeText(view.getContext(), "Start date must be small than End date ", Toast.LENGTH_SHORT).show();
                } else {
                    roomLayout.setVisibility(View.VISIBLE);
                    List<Room> getRoomsByDate = filterRoomByDate(billDao, start, end);
                    roomAdapter = new RoomSpinnerAdapter(view.getContext(), R.layout.item_room_spinner, getRoomsByDate);
                    spnRoom.setAdapter(roomAdapter);
                    billStartDate = dateFormat.format(start.getTime());
                    billEndDate = dateFormat.format(end.getTime());
                }
            }
        });

        spnCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerId = getListCustomer().get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomId = getRooms().get(position).getId();
                totalBill.setText(getRooms().get(position).getPrice() + "");
                billTotal = Integer.parseInt(totalBill.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addBillBtn.setOnClickListener(button -> {
            Bill bill = new Bill();
            bill.setCustomerId(customerId);
            bill.setRoomId(roomId);
            bill.setFromDate(billStartDate);
            bill.setEndDate(billEndDate);
            bill.setStatus(1);
            bill.setNote(tvNote.getText().toString());
            bill.setBillDate(getNowDate());
            bill.setBillTotal(billTotal);
            bill.setRoomPrice(0);
            bill.setServicePrice(0);
            if (billDao.insert(bill) > 0) {
                Toast.makeText(view.getContext(), "Add bill success", Toast.LENGTH_SHORT).show();
                dialog.cancel();
                getBillRecyclerView(view);
            } else {
                Toast.makeText(view.getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void getBillRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.rcv_bill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        List<Bill> billList = getBillList(billDao);


        Spinner spnRoomStatus = view.findViewById(R.id.spinner_status_hd);
        statusAdapter = new StatusSpinnerAdapter(view.getContext(), R.layout.item_status_spinner_selected, getListStatus());
        spnRoomStatus.setAdapter(statusAdapter);

        spnRoomStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int statusId = statusAdapter.getItem(position).id;
                switch (statusId) {
                    case 1:
                        //not check in
                        List<Bill> billNotCheckIns = billList.stream()
                                .filter(bill -> bill.getStatus() == 1)
                                .collect(Collectors.toList());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(new BillAdapter(billNotCheckIns, getContext()));
                        break;
                    case 2:
                        List<Bill> billCheckIns = billList.stream()
                                .filter(bill -> bill.getStatus() == 2)
                                .collect(Collectors.toList());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(new BillAdapter(billCheckIns, getContext()));
                        break;
                    case 3:
                    default:
                        List<Bill> billCheckOuts = billList.stream()
                                .filter(bill -> bill.getStatus() == 3)
                                .collect(Collectors.toList());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(new BillAdapter(billCheckOuts, getContext()));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getNowDate() {
        Calendar date = Calendar.getInstance();
        GregorianCalendar c = new GregorianCalendar(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        return dateFormat.format(c.getTime());
    }

    private GregorianCalendar convertStringToDate(String dateFormat) {
        String[] s = dateFormat.split("/");
        int year = Integer.parseInt(s[2]);
        int month = Integer.parseInt(s[1]);
        int day = Integer.parseInt(s[0]);
        return new GregorianCalendar(year, month, day);
    }

    private List<Room> filterRoomByDate(BillDAO billDAO, GregorianCalendar start, GregorianCalendar end) {
        List<Bill> filterBills = getBillList(billDAO).stream().filter(bill -> {
            GregorianCalendar billStart = convertStringToDate(bill.getFromDate());
            GregorianCalendar billEnd = convertStringToDate(bill.getEndDate());
            return billStart.compareTo(start) <= 0 && billEnd.compareTo(start) <= 0 || billStart.compareTo(end) <= 0 && billEnd.compareTo(end) <= 0;
        }).collect(Collectors.toList());

        List<Integer> filterRoomIds = filterBills.stream()
                .map(Bill::getRoomId)
                .collect(Collectors.toList());

        return getRooms().stream()
                .filter(room -> !filterRoomIds.contains(room.getId()))
                .collect(Collectors.toList());
    }

    private List<Bill> getBillList(BillDAO billDAO) {
        return billDAO.getAll();
    }

    private List<RoomStatus> getListStatus() {
        //todo: Tr??? v??? list c??c status c?? trong db (t???m th???i fix c???ng data)
        List<RoomStatus> roomStatuses = new ArrayList<>();
        roomStatuses.add(new RoomStatus(1, "Chua nhan phong"));
        roomStatuses.add(new RoomStatus(2, "Chua tra"));
        roomStatuses.add(new RoomStatus(3, "Da tra"));
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