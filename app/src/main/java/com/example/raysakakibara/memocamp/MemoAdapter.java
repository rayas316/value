package com.example.raysakakibara.memocamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MemoAdapter extends ArrayAdapter<Memo> {
    public LayoutInflater layoutInflater;


    MemoAdapter(Context context, int textViewResourceId, List<Memo> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Memo memo = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.memo_item_layout, null);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        TextView contentsText = (TextView) convertView.findViewById(R.id.contentText);
        TextView valueOfEverydayText = (TextView) convertView.findViewById(R.id.valueOfEverydayText);
        TextView dateText=(TextView)convertView.findViewById(R.id.dateView);

        titleText.setText(memo.title);
        contentsText.setText(memo.content + "¥");
        Date date1 = new Date();
        Date date2 = memo.date;
        long datetime1 = date1.getTime();
        long datetime2 = date2.getTime();
        long one_date_time = 24 * 60 * 60 * 1000;
        long diffDays = (datetime1 - datetime2) / one_date_time;
        if (diffDays == 0) {
            diffDays = 1;
        }
        long value1 = Long.parseLong(memo.content);
        long value2 = (value1 / diffDays);
        valueOfEverydayText.setText(String.valueOf(value2)+"¥");
        dateText.setText(String.valueOf(diffDays)+"日前");

        return convertView;

    }
}