package com.example.calendar.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.calendar.Dao.SuKienDao;
import com.example.calendar.EventEditActivity;
import com.example.calendar.Model.LoaiSuKien;
import com.example.calendar.Model.SuKien;
import com.example.calendar.R;
import com.example.calendar.SearchActivity;
import com.example.calendar.WeekViewActivity;

import java.util.List;

public class SuKienAdapter extends BaseAdapter {

    private Context context;
    private List<SuKien> list;
    private List<LoaiSuKien> listLSK;

    public int _id;

    SuKienDao SKD;

    public SuKienAdapter(Context context, List<SuKien> list){
        this.context = context;
        this.list = list;
    }
    public SuKienAdapter(Context context, List<SuKien> list, List<LoaiSuKien> listLSK){
        this.context = context;
        this.list = list;
        this.listLSK = listLSK;
    }
    public void updateData (List<SuKien> list){
        list.clear();
        list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf = ((Activity) context).getLayoutInflater();
        convertView = inf.inflate(R.layout.event_cell, null);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
        TextView item_sk_lsk = convertView.findViewById(R.id.item_sk_lsk);
        TextView item_sk_tgbd = convertView.findViewById(R.id.item_sk_tgbd);
        TextView item_sk_tgkt = convertView.findViewById(R.id.item_sk_tgkt);
        TextView item_sk_tt = convertView.findViewById(R.id.item_sk_tt);
        TextView id_event = convertView.findViewById(R.id.id_event);

        Button bt_delete_SK = convertView.findViewById(R.id.bt_delete_SK);
        Button bt_create_SK = convertView.findViewById(R.id.bt_create_SK);

        SKD = new SuKienDao(convertView.getContext());


        SuKien suKien = list.get(position);
        _id = suKien.getMaSK();
        id_event.setText(_id +"");
        Log.d("TAG", "getView2: "+position);


        try {

            eventCellTV.setText(suKien.getNoiDung());
            item_sk_tgbd.setText(suKien.getTGBD());
            item_sk_tgkt.setText(suKien.getTGKT());


            String lsk = listLSK.get(suKien.getMaLSK()).getTenLoai();
            item_sk_lsk.setText(lsk);
            Log.d("SuKienAdapter", "1243: "+suKien.getTrangThai());
            String TrangThai = "Hoàn thành";
            if (suKien.getTrangThai() == 0){
                TrangThai = "Chưa hoàn thành";
            }
            item_sk_tt.setText(TrangThai);
        }catch (Exception e){
            Log.d("SuKienAdapter", "err: "+e);
        }

        // Xóa sản phẩn
        bt_delete_SK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_id(Integer.parseInt(id_event.getText().toString()));
                xoa(get_id(), context);

            }
        });

        // Sửa sản phẩm
        bt_create_SK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_id(Integer.parseInt(id_event.getText().toString()));
                Intent i = new Intent(context, EventEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("key_update", 1);
                bundle.putInt("key_SK", get_id());
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    // Hiện thi dialog cảnh báo khi xóa

    public void xoa(final int Id, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc chắn muốn xóa không");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", (dialog, id) -> {
            SKD.delete(_id);
            try {
                ((WeekViewActivity)context).setEventAdpater();
            }catch (Exception e){
                ((SearchActivity)context).setEventAdpater();
            }finally {

            }
            dialog.cancel();
        });
        builder.setNegativeButton("No", (dialog, id) ->{
            dialog.cancel();
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
