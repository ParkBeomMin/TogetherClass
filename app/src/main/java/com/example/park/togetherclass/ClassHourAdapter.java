package com.example.park.togetherclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Park on 2017-06-05.
 */

public class ClassHourAdapter extends BaseAdapter {
    public ClassHourAdapter(Context c, ArrayList<ClassHour> arrayList) {
        this.c = c;
        this.arrayList = arrayList;
    }

    ArrayList<ClassHour> arrayList = new ArrayList<ClassHour>();
    Context c;

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(c);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.free_list, null);
        }
        ImageView i1 = (ImageView) convertView.findViewById(R.id.freeimage);
        i1.setVisibility(View.GONE);
        TextView t1 = (TextView) convertView.findViewById(R.id.freetitle);
        TextView t2 = (TextView) convertView.findViewById(R.id.freenick);
        TextView t3 = (TextView) convertView.findViewById(R.id.freedate);

        ClassHour one;
        one = arrayList.get(position);
        t1.setText(one.Content);
        t2.setText(one.Nick);
        t3.setText(one.Date);
        return convertView;
    }
}
