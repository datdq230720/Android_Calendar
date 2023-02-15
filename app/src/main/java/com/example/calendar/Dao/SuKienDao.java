package com.example.calendar.Dao;

import static com.example.calendar.Utils.constant.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.calendar.Database.database;
import com.example.calendar.Model.LoaiSuKien;
import com.example.calendar.Model.SuKien;

import java.util.ArrayList;
import java.util.List;

public class SuKienDao {
    database db;
    SQLiteDatabase SQL;

    public SuKienDao(Context c){
        db = database.getDbInstance(c);
    }

    // lấy toàn bộ danh sách sự kiên

    public List<SuKien> getAll() {
        List<SuKien> list = new ArrayList<>();
        String q = " select * from " + TABLE_SK ;
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q, null);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                Integer maSK = cursor.getInt(0);
                String Noidung = cursor.getString(1);
                String TGBD = cursor.getString(2);
                String TGKT = cursor.getString(3);
                String Ngay = cursor.getString(4);
                Integer TrangThai = cursor.getInt(5);
                String GhiChu = cursor.getString(6);
                Integer Qtrong = cursor.getInt(7);
                Integer MaLSK = cursor.getInt(8);

                SuKien suKien = new SuKien(maSK, Noidung, TGBD, TGKT, Ngay, TrangThai, GhiChu, Qtrong, MaLSK);
                list.add(suKien);
                Log.d("SuKienDao", "get: Successful"+maSK);
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

    // Lấy ds sự kiện theo id

    public SuKien getById(int _id) {
        SuKien suKien = new SuKien();
        String q = " select * from " + TABLE_SK + " where " + COLUMN_MaSK + " = ? ";
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q,  new String[]{String.valueOf(_id)});
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                Integer maSK = cursor.getInt(0);
                String Noidung = cursor.getString(1);
                String TGBD = cursor.getString(2);
                String TGKT = cursor.getString(3);
                String Ngay = cursor.getString(4);
                Integer TrangThai = cursor.getInt(5);
                String GhiChu = cursor.getString(6);
                Integer Qtrong = cursor.getInt(7);
                Integer MaLSK = cursor.getInt(8);

                suKien = new SuKien(maSK, Noidung, TGBD, TGKT, Ngay, TrangThai, GhiChu, Qtrong, MaLSK);
                Log.d("SuKienDao", "get: Successful"+maSK);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get LoaiSuKien error: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return suKien;
    }

    // Lấy danh sách sự kiện theo ngày

    public List<SuKien> getByDate(String _date) {
        List<SuKien> list = new ArrayList<>();
        SuKien suKien = new SuKien();
        String q = " select * from " + TABLE_SK + " where " + COLUMN_Ngay + " = ? ";
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q,  new String[]{String.valueOf(_date)});
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                Integer maSK = cursor.getInt(0);
                String Noidung = cursor.getString(1);
                String TGBD = cursor.getString(2);
                String TGKT = cursor.getString(3);
                String Ngay = cursor.getString(4);
                Integer TrangThai = cursor.getInt(5);
                String GhiChu = cursor.getString(6);
                Integer Qtrong = cursor.getInt(7);
                Integer MaLSK = cursor.getInt(8);

                suKien = new SuKien(maSK, Noidung, TGBD, TGKT, Ngay, TrangThai, GhiChu, Qtrong, MaLSK);
                list.add(suKien);
                Log.d("SuKienDao", "get: Successful"+Ngay);
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

    // Lấy danh sách sự kiện theo Nội dung

    public List<SuKien> getByName(String _name) {
        String name = "'%"+_name+"%'";
        List<SuKien> list = new ArrayList<>();
        SuKien suKien = new SuKien();
        String q = " select * from " + TABLE_SK + " where " + COLUMN_NoiDung + " like " + name ;
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q,  null);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                Integer maSK = cursor.getInt(0);
                String Noidung = cursor.getString(1);
                String TGBD = cursor.getString(2);
                String TGKT = cursor.getString(3);
                String Ngay = cursor.getString(4);
                Integer TrangThai = cursor.getInt(5);
                String GhiChu = cursor.getString(6);
                Integer Qtrong = cursor.getInt(7);
                Integer MaLSK = cursor.getInt(8);

                suKien = new SuKien(maSK, Noidung, TGBD, TGKT, Ngay, TrangThai, GhiChu, Qtrong, MaLSK);
                Log.d("TAG", "getByName: ");
                list.add(suKien);
                Log.d("SuKienDao", "get Successful: "+TrangThai);
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


    // Lấy số lượng sự kiện theo ngày và theo Tình trạng
    public int getCount(String ngay, int TT) {
        String name = "'%" + ngay + "%'";
        int count = 0;
        String q = " select count(MaSK) as number from " + TABLE_SK + " where " + COLUMN_Ngay + " like " + name + " and " + COLUMN_Trangthai + " = " + TT;

        Log.d(">><<<<>>>>>>>", "getCount: "+q);
        SQLiteDatabase sdb = db.getReadableDatabase();
        Cursor cursor = sdb.rawQuery(q,  null);
        cursor.moveToFirst();
        try {
            while (cursor.isAfterLast() == false){
                count = cursor.getInt(0);
                Log.d(">>>>>>>>>", "getCount: "+count);
                cursor.moveToNext();
            }
        }catch (Exception e){
            Log.e("Get LoaiSuKien error: ", e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }

        return count;
    }


    // Thêm sự kiện vào database

    public void insert (SuKien suKien){
        SQL = db.getWritableDatabase();
        SQL.beginTransaction();
        try {
            ContentValues values = new ContentValues();
//            values.put(COLUMN_MaSK, suKien.getMaSK());
            values.put(COLUMN_NoiDung, suKien.getNoiDung());
            values.put(COLUMN_TGBD, suKien.getTGBD());
            values.put(COLUMN_TGKT, suKien.getTGKT());
            values.put(COLUMN_Ngay, suKien.getNgay());
            values.put(COLUMN_Trangthai, suKien.getTrangThai());
            values.put(COLUMN_Ghichu, suKien.getGhiChu());
            values.put(COLUMN_QTrong, suKien.getQtrong());
            values.put(COLUMN_MaLoai, suKien.getMaLSK());
            SQL.insertOrThrow(TABLE_SK, null, values);
            SQL.setTransactionSuccessful();
            Log.d("SuKienDao", "insert: Successful");
        }catch (Exception e){
            Log.e("Insert SuKienDao error: ", e.getMessage());
        }finally {
            SQL.endTransaction();
        }
    }


    // Cập nhật sự kiện

    public void update (SuKien suKien){
        SQL = db.getWritableDatabase();
        SQL.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_MaSK, suKien.getMaSK());
            values.put(COLUMN_NoiDung, suKien.getNoiDung());
            values.put(COLUMN_TGBD, suKien.getTGBD());
            values.put(COLUMN_TGKT, suKien.getTGKT());
            values.put(COLUMN_Ngay, suKien.getNgay());
            values.put(COLUMN_Trangthai, suKien.getTrangThai());
            values.put(COLUMN_Ghichu, suKien.getGhiChu());
            values.put(COLUMN_QTrong, suKien.getQtrong());
            values.put(COLUMN_MaLoai, suKien.getMaLSK());
            SQL.update(TABLE_SK, values,
                    COLUMN_MaSK + " = ? ",
                    new String[]{String.valueOf(suKien.getMaSK())});
            SQL.setTransactionSuccessful();
            Log.d("SuKienDao", "update: Successful");
        }catch (Exception e){
            Log.e("Update SuKienDao error: ", e.getMessage());
        }finally {
            SQL.endTransaction();
        }
    }
    public void delete(int id){
        SQL = db.getWritableDatabase();
        SQL.beginTransaction();
        try {
            SQL.delete(TABLE_SK,
                    COLUMN_MaSK  + " = ? ",
                    new String[]{String.valueOf(id)});
            SQL.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("Insert category error: ", e.getMessage());
        }finally {
            SQL.endTransaction();
        }
    }
}
