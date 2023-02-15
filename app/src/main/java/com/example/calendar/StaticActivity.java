package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.calendar.Dao.SuKienDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;


public class StaticActivity extends AppCompatActivity {

    EditText ed_static_ngay;
//    PieChart pieChart;



    SuKienDao skD;
    int HT = 0,KHT = 0;
    String ngay = "", nameChart = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);

        ed_static_ngay = findViewById(R.id.ed_static_ngay);
//        pieChart = findViewById(R.id.piechart);
//        bt_static = findViewById(R.id.bt_static);




        skD = new SuKienDao(StaticActivity.this);
        HT = skD.getCount("", 1);
        KHT = skD.getCount("", 0);


        setChart(HT, KHT);
        // Dữ liệu cho biểu đồ



    }


    public void setData(View view){

        ngay = ed_static_ngay.getText().toString();

        if (ngay.length() == 0){
            nameChart = "ALL";
        }else {
            nameChart = ngay;
        }

        skD = new SuKienDao(StaticActivity.this);
        HT = skD.getCount(ngay, 1);
        KHT = skD.getCount(ngay, 0);

        Log.d("TAG", "setData: "+nameChart);
        setChart(HT, KHT);

    }

    public void DialogCalendar(View view)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(StaticActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String d = "";
                if (dayOfMonth < 10){
                    d = "0";
                }
                ed_static_ngay.setText( year + "-" + (monthOfYear + 1) + "-"+ d + dayOfMonth + "");
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public  void setChart(int _HT, int _KHT){

        PieChart pieChart = findViewById(R.id.piechart);

        ArrayList<PieEntry> list = new ArrayList<>();

        Log.d("TAG", "setChart: "+ _HT +nameChart + _KHT);

        list.add(new PieEntry(_HT, "Hoàn thành"));
        list.add(new PieEntry(_KHT, "Chưa hoàn thành"));

        PieDataSet pieDataSet = new PieDataSet(list, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(20f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(nameChart);
        pieChart.animate();


    }



}