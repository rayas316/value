package ray.io.raysakakibara.prive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

public class CardAdapter extends ArrayAdapter<Card> {
    public LayoutInflater layoutInflater;


    CardAdapter(Context context, int textViewResourceId, List<Card> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = getItem(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(ray.io.raysakakibara.prive.R.layout.layout_item_memo, null);
        }

        TextView titleText = (TextView) convertView.findViewById(ray.io.raysakakibara.prive.R.id.titleText);
        TextView contentsText = (TextView) convertView.findViewById(ray.io.raysakakibara.prive.R.id.contentText);
        TextView valueOfEverydayText = (TextView) convertView.findViewById(ray.io.raysakakibara.prive.R.id.valueOfEverydayText);
        TextView dateText = (TextView) convertView.findViewById(ray.io.raysakakibara.prive.R.id.dateView);

        titleText.setText(card.title);
        contentsText.setText(card.content + "¥");
        Date date1 = new Date();
        Date date2 = card.date;
        long datetime1 = date1.getTime();
        long datetime2 = date2.getTime();
        long one_date_time = 24 * 60 * 60 * 1000;
        long diffDays = (datetime1 - datetime2) / one_date_time;
        if (diffDays == 0) {
            diffDays = 1;
        }
        long value1 = Long.parseLong(card.content);
        long value2 = (value1 / diffDays);
        valueOfEverydayText.setText(String.valueOf(value2) + "¥");
        dateText.setText(String.valueOf(diffDays) + "日前");

        return convertView;

    }
}