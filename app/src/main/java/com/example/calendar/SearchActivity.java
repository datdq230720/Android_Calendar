package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.calendar.Adapter.LoaiSuKienAdapter;
import com.example.calendar.Adapter.SuKienAdapter;
import com.example.calendar.Dao.LoaiSuKienDao;
import com.example.calendar.Dao.SuKienDao;
import com.example.calendar.Model.LoaiSuKien;
import com.example.calendar.Model.SuKien;
import com.example.calendar.R;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Button bt_search_from_calendar;
    ImageView bt_search;
    private EditText ed_search;
    private ListView lv_search;

    SuKienDao skD;
    LoaiSuKienDao lskD;
    LoaiSuKienAdapter lskA;
    SuKienAdapter skA;
    List<SuKien> listSK;
    List<LoaiSuKien> listLSK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ed_search = findViewById(R.id.ed_search);
        bt_search_from_calendar = findViewById(R.id.bt_search_from_calendar);
        bt_search = findViewById(R.id.bt_search);
        lv_search = findViewById(R.id.evenLV);

        skD = new SuKienDao(this);
        lskD = new LoaiSuKienDao(this);
        setEventAdpater();




    }


    public void Search(View view){
        setEventAdpater();
    }

    public void setEventAdpater(){
        String name = ed_search.getText().toString();
        listSK = skD.getByName(name);
        listLSK = lskD.get();
        skA = new SuKienAdapter(this, listSK, listLSK);
        lv_search.setAdapter(skA);
    }

    public void searchFromCalendar(View view){
        Intent i = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(i);
    }
}