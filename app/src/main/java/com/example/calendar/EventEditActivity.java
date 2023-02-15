package com.example.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.calendar.Adapter.LoaiSuKienAdapter;
import com.example.calendar.Adapter.SuKienAdapter;
import com.example.calendar.Dao.LoaiSuKienDao;
import com.example.calendar.Dao.SuKienDao;
import com.example.calendar.Model.Event;
import com.example.calendar.Model.LoaiSuKien;
import com.example.calendar.Model.SuKien;
import com.example.calendar.Utils.CalendarUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameND, eventDateED, eventTimeStart, eventTimeEnd, eventGhiChu;
//    private TextView eventTimeTV, eventDateTV;
    private LocalTime time;
    private Date createdDate = new Date();
    private Spinner spinner_loai, sp2;
    private CheckBox cb_ht, cb_qt;
//    RadioButton rb_ht, rb_qt;

    private LoaiSuKienAdapter LSKAdapter;
    private LoaiSuKienDao LSKDao;
    List<LoaiSuKien> listLSK;

    private SuKienAdapter SKAdapter;
    private SuKienDao SKDao;
    List<SuKien> listSk;
    SuKien suKien;

    int isUpdate = 0;
    int _id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidgets();
        time = LocalTime.now();
//        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventDateED.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
//        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
        eventTimeStart.setText(CalendarUtils.formattedShortTime(time));
        eventTimeEnd.setText("23:59");

        listLSK =(new LoaiSuKienDao(EventEditActivity.this)).get();
        LSKAdapter = new LoaiSuKienAdapter(EventEditActivity.this, listLSK);
        spinner_loai.setAdapter(LSKAdapter);
        spinner_loai.setSelection(1);

        SKDao = new SuKienDao(EventEditActivity.this);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        if (bundle != null) {
            isUpdate =  bundle.getInt("key_update");
            _id = bundle.getInt("key_SK");
        }

        String[] arr = { "Trước 5 phút", "Trước 15 phút", "Trước 30 phút", "Trước 1 tiếng", "trước 1 ngày" };
        List<String> listH = Arrays.asList(arr);
        Log.d(">>>>>>>>>>>", "onCreate: "+listH.size());
        ArrayAdapter adapterspiner = new ArrayAdapter(EventEditActivity.this, android.R.layout.simple_list_item_1, listH);
        sp2.setAdapter(adapterspiner);
        sp2.setSelection(0);

        if (isUpdate != 0){
            SKDao = new SuKienDao(this);
            suKien = SKDao.getById(_id);

            eventNameND.setText(suKien.getNoiDung().toString());
            eventDateED.setText(suKien.getNgay().toString());
            eventTimeStart.setText(suKien.getTGBD().toString());
            eventTimeEnd.setText(suKien.getTGKT().toString());
            eventGhiChu.setText(suKien.getGhiChu().toString());

            if (suKien.getTrangThai() == 1){
                cb_ht.setChecked(true);
            }
            if (suKien.getQtrong() == 1){
                cb_qt.setChecked(true);
            }

            spinner_loai.setSelection(suKien.getMaLSK());

        }


    }

    // Khai báo ánh xạ cách phần tử ở layout
    private void initWidgets()
    {
        eventNameND = findViewById(R.id.eventNameND);
        eventDateED = findViewById(R.id.eventDateED);

//        eventDateTV = findViewById(R.id.eventDateTV);
//        eventTimeTV = findViewById(R.id.eventTimeTV);

        eventTimeStart = findViewById(R.id.eventTimeStart);
        eventTimeEnd = findViewById(R.id.eventTimeEnd);
        spinner_loai = findViewById(R.id.spinner_loai);
        eventGhiChu = findViewById(R.id.eventGhiChu);
        cb_ht = findViewById(R.id.cb_ht);
        cb_qt = findViewById(R.id.cb_qt);
        sp2 = findViewById(R.id.spinner2);
//        rb_qt  = findViewById(R.id.rb_qt);
//        rb_ht = findViewById(R.id.rb_ht);


    }

    public void saveEventAction(View view)
    {
        String ND = eventNameND.getText().toString();
        String Ngay = eventDateED.getText().toString();
        String Start = eventTimeStart.getText().toString();
        String End = eventTimeEnd.getText().toString();
        String GhiChu = eventGhiChu.getText().toString();

        int loai = spinner_loai.getSelectedItemPosition();

        int ht = 0;
        if(cb_ht.isChecked()){
            ht = 1;
        }else{
            ht = 0;
        }
        int qt = 0;
        if(cb_qt.isChecked()){
            qt = 1;
        }else{
            qt = 0;
        }

        if (isUpdate == 0 && validation()){
            suKien = new SuKien(ND, Start, End, Ngay, ht, GhiChu, qt, loai);
            SKDao.insert(suKien);
            finish();
        }else if(isUpdate == 1 && validation()){
            suKien = new SuKien(_id, ND, Start, End, Ngay, ht, GhiChu, qt, loai);
            SKDao.update(suKien);
            finish();
        }

//        Event newEvent = new Event(ND, CalendarUtils.selectedDate, time);
//        Event.eventsList.add(newEvent);
//        finish();
    }

    public void cancleEventAction(View view)
    {
//        Intent i = new Intent(this, WeekViewActivity.class);
//        startActivity(i);
        finish();
    }

    // Chức năng chọn ngày bắt

    public void DialogCalendar(View view)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(EventEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String d = "";
                if (dayOfMonth < 10){
                    d = "0";
                }
                eventDateED.setText( year + "-" + (monthOfYear + 1) + "-"+d+ dayOfMonth + "");

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    // Chọn thời gian bắt đầu
    public void DialogTimeStart(View view)
    {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(EventEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String h = "";
                if (hourOfDay < 10){
                    h = "0";
                }
                String m = "";
                if (minute < 10){
                    m = "0";
                }
                eventTimeStart.setText(h+hourOfDay + ":" +m+ minute);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    // Chọn thời gian kết thúc
    public void DialogTimeEnd(View view)
    {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(EventEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String h = "";
                if (hourOfDay < 10){
                    h = "0";
                }
                String m = "";
                if (minute < 10){
                    m = "0";
                }
                eventTimeEnd.setText(h+hourOfDay + ":" +m+ minute);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    // Bắt lỗi khi người dùng lưu hoặc thêm mới sự kiện
    public boolean validation(){

        boolean status = true;

        String ND = eventNameND.getText().toString();
        String Ngay = eventDateED.getText().toString();
        String Start = eventTimeStart.getText().toString();
        String End = eventTimeEnd.getText().toString();
        String GhiChu = eventGhiChu.getText().toString();

        if (ND.length() == 0){
            status = false;
            Toast.makeText(this, "Bạn chưa nhập tên sự kiện", Toast.LENGTH_SHORT).show();
        } else if (Ngay.length() == 0){
            status = false;
            Toast.makeText(this, "Bạn chưa chọn ngày", Toast.LENGTH_SHORT).show();
        } else if (Start.length() == 0){
            status = false;
            Toast.makeText(this, "Bạn chưa chọn thời gian bắt đầu", Toast.LENGTH_SHORT).show();
        } else if (End.length() == 0){
            status = false;
            Toast.makeText(this, "Bạn chưa chọn thời gian kết thúc", Toast.LENGTH_SHORT).show();
        }


            return status;
    }
}