package com.example.raysakakibara.memocamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class ListAdapter extends ArrayAdapter<Memo> {
    public LayoutInflater layoutInflater;

    ListAdapter(Context context, int textViewResourceId, List<Memo> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Memo memo = getItem(position);
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.layoutitemmemo, null);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.title);
        TextView contentsText = (TextView) convertView.findViewById(R.id.content);

        titleText.setText(memo.titles);
        contentsText.setText(memo.contents);


        return convertView;

    }
}
