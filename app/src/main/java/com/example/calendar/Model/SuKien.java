package com.example.calendar.Model;

public class SuKien {

    private Integer MaSK;
    private String NoiDung;
    private String TGBD;
    private String TGKT;
    private String Ngay;
    private Integer TrangThai;
    private String GhiChu;
    private Integer Qtrong;
    private Integer MaLSK;

    public SuKien() {
    }

    public SuKien(String noiDung, String TGBD, String TGKT, String ngay,
                  Integer trangThai, String ghiChu, Integer qtrong, Integer maLSK) {
        NoiDung = noiDung;
        this.TGBD = TGBD;
        this.TGKT = TGKT;
        Ngay = ngay;
        TrangThai = trangThai;
        GhiChu = ghiChu;
        Qtrong = qtrong;
        MaLSK = maLSK;
    }

    public SuKien(Integer maSK, String noiDung, String TGBD, String TGKT, String ngay,
                  Integer trangThai, String ghiChu, Integer qtrong, Integer maLSK) {
        MaSK = maSK;
        NoiDung = noiDung;
        this.TGBD = TGBD;
        this.TGKT = TGKT;
        Ngay = ngay;
        TrangThai = trangThai;
        GhiChu = ghiChu;
        Qtrong = qtrong;
        MaLSK = maLSK;
    }

    public Integer getMaSK() {
        return MaSK;
    }

    public void setMaSK(Integer maSK) {
        MaSK = maSK;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getTGBD() {
        return TGBD;
    }

    public void setTGBD(String TGBD) {
        this.TGBD = TGBD;
    }

    public String getTGKT() {
        return TGKT;
    }

    public void setTGKT(String TGKT) {
        this.TGKT = TGKT;
    }

    public Integer getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(Integer trangThai) {
        TrangThai = trangThai;
    }

    public String getNgay() {
        return Ngay;
    }

    public void setNgay(String ngay) {
        Ngay = ngay;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public Integer getQtrong() {
        return Qtrong;
    }

    public void setQtrong(Integer qtrong) {
        Qtrong = qtrong;
    }

    public Integer getMaLSK() {
        return MaLSK;
    }

    public void setMaLSK(Integer maLSK) {
        MaLSK = maLSK;
    }
}
