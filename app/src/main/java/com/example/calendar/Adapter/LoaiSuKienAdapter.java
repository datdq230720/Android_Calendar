package com.example.calendar.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.calendar.Model.LoaiSuKien;
import com.example.calendar.R;

import java.util.List;

public class LoaiSuKienAdapter extends BaseAdapter {

    private Context context;
    List<LoaiSuKien> list;

    public LoaiSuKienAdapter(Context c, List<LoaiSuKien> list){
        this.context = c;
        this.list = list;
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
        View view = convertView;
        if (view == null) {
            view = View.inflate(parent.getContext(), R.layout.item_loai_su_kien, null);
            TextView textView = (TextView) view.findViewById(R.id.item_lsk_tv);
            ViewHolder holder = new ViewHolder(textView);
            view.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        LoaiSuKien loaiSuKien = (LoaiSuKien) getItem(position);
        holder.textView.setText(loaiSuKien.getTenLoai());
        return view;
    }

    private static class ViewHolder {
        final TextView textView;

        public ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}
