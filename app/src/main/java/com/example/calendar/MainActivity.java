package com.example.calendar;

import static com.example.calendar.Utils.CalendarUtils.daysInMonthArray;
import static com.example.calendar.Utils.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.Adapter.CalendarAdapter;
import com.example.calendar.Dao.SuKienDao;
import com.example.calendar.Utils.CalendarUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    SuKienDao skD;
    int HT = 0,KHT = 0;

    TextView tv_home;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skD = new SuKienDao(this);

        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
    }


    // Khai báo ánh xạ cách phần tử ở layout

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
//        tv_home = findViewById(R.id.tv_home);
    }


    // load lại dữ liệu tháng của bảng lịch

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // chức năng đổi sang tháng trước đó

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    // chức năng đổi sang tháng trước tiếp theo

    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            HT = skD.getCount(CalendarUtils.formattedDate(date), 1);
            KHT = skD.getCount(CalendarUtils.formattedDate(date), 0);
//            tv_home.setText("Có " + HT + " sự kiện hoàn thành và " + KHT +
//                    " sự kiện chưa hoàn thành");
            Toast.makeText(this, "Có " + HT + " sự kiện hoàn thành và " + KHT +
                    " sự kiện chưa hoàn thành", Toast.LENGTH_SHORT).show();
            setMonthView();
        }
    }

    // chuyển sang màn hình danh sách sự kiện
    public void weeklyAction(View view)
    {
        Intent i = new Intent(MainActivity.this, WeekViewActivity.class);
        startActivity(i);
    }

    // chuyển sang màn hình tìm kiếm
    public void searchAction(View view)
    {
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(i);
    }

    // chuyển sang màn hình thống kê
    public void staticAction(View view)
    {
        Intent i = new Intent(MainActivity.this, StaticActivity.class);
        startActivity(i);
    }
}








