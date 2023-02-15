package com.example.calendar.Dao;

import static com.example.calendar.Utils.constant.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.calendar.Database.database;
import com.example.calendar.Model.LoaiSuKien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSuKienDao  {

    database db;

    public LoaiSuKienDao(Context c){
        db = database.getDbInstance(c);
    }


    // Lấy danh sách toàn bộ loại sự kiện'

    public List<LoaiSuKien> get() {
        List<LoaiSuKien> list = new ArrayList<>();
        String q = " select * from " + TABLE_LSK ;
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q, null);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                LoaiSuKien loaiSuKien = new LoaiSuKien(id, name);
                list.add(loaiSuKien);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get LoaiSuKien error: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return list;
    }


}
