package com.example.calendar.Model;

public class LoaiSuKien {

    private int MaLoai;
    private String TenLoai;

    public LoaiSuKien() {
    }

    public LoaiSuKien(int maLoai, String tenLoai) {
        MaLoai = maLoai;
        TenLoai = tenLoai;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }
}
