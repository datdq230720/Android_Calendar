package com.example.calendar;

import static com.example.calendar.Utils.CalendarUtils.daysInWeekArray;
import static com.example.calendar.Utils.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.Adapter.CalendarAdapter;
import com.example.calendar.Adapter.EventAdapter;
import com.example.calendar.Adapter.SuKienAdapter;
import com.example.calendar.Dao.LoaiSuKienDao;
import com.example.calendar.Dao.SuKienDao;
import com.example.calendar.Model.Event;
import com.example.calendar.Model.LoaiSuKien;
import com.example.calendar.Model.SuKien;
import com.example.calendar.Utils.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;

    SuKienAdapter SKAdapter;
    SuKienDao SKDao;
    List<SuKien> listSK;
    SuKien suKien;

    List<LoaiSuKien> listLSK;
    LoaiSuKienDao lskDao;

    String _date;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();

        SKDao = new SuKienDao(WeekViewActivity.this);
        lskDao = new LoaiSuKienDao(WeekViewActivity.this);


        setWeekView();
    }
    // Khai báo ánh xạ cách phần tử ở layout
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    // load lại dữ liệu tuần của bảng lịch
    private void setWeekView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
//

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdpater();
    }

    // chức năng đổi sang tuần trước đó
    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    // chức năng đổi sang tuần trước tiếp theo
    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        _date = CalendarUtils.formattedDate(date);
//        Toast.makeText(this, "Bạn đã chọn ngày: "+ CalendarUtils.formattedDate(date), Toast.LENGTH_SHORT).show();
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdpater();
    }

    // Load lại danh sách sự kiện

    public void setEventAdpater()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);

        listLSK = lskDao.get();
//        listSK = SKDao.getAll();
        listSK = SKDao.getByDate(CalendarUtils.selectedDate.toString());
//        Toast.makeText(this, ">>>>>"+CalendarUtils.selectedDate, Toast.LENGTH_SHORT).show();
        SKAdapter = new SuKienAdapter(WeekViewActivity.this, listSK, listLSK);
        eventListView.setAdapter(SKAdapter);
    }

    // Chức năng thêm sự kiện mới
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    // Quay về màn hình chính
    public void dailyAction(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
    }
}