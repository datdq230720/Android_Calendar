package com.example.calendar.Database;

import static com.example.calendar.Utils.constant.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    private static database dbInstance;
    public static synchronized database getDbInstance(Context c){
        if (dbInstance == null){
            dbInstance = new database(c);
        }
        return dbInstance;
    }
    public database( Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = " create table if not exists " + TABLE_LSK +
                "(" +
                COLUMN_MaLSK +
                " integer primary key autoincrement," +
                COLUMN_TenLSK + " text " +
                ")";

        db.execSQL(sql1);

        String sql2 = "create table if not exists " + TABLE_SK +
                "(" +
                COLUMN_MaSK + " integer primary key autoincrement," +
                COLUMN_NoiDung + " text," +
                COLUMN_TGBD + " text," +
                COLUMN_TGKT + " text," +
                COLUMN_Ngay + " text," +
                COLUMN_Trangthai + " integer," +
                COLUMN_Ghichu + " text, "+
                COLUMN_QTrong + " integer, "+
                COLUMN_MaLoai + " integer, "+
                " FOREIGN KEY ( "+COLUMN_MaLoai+" ) " +
                " REFERENCES "+TABLE_LSK+" ( "+COLUMN_MaLSK+" ) " +
                " ) ";
        db.execSQL(sql2);



        sql1 = " insert into " + TABLE_LSK + " values ( null, '' ), (null, 'Sinh nhật'), (null , 'Đi chơi'), (null , 'Mua sắm'), (null , 'Công việc')";
        db.execSQL(sql1);



    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgrade = oldVersion + 1;
        while (upgrade < newVersion){
            switch (upgrade){
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                default:

                    break;
            }
            upgrade++;
        }
    }
}
