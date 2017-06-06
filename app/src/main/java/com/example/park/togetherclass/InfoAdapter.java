package com.example.park.togetherclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Park on 2017-05-30.
 */

public class InfoAdapter extends BaseAdapter {
    ArrayList<Info> arrayList = new ArrayList<Info>();
    Context c;

    public InfoAdapter(ArrayList<Info> arrayList, Context c) {
        this.arrayList = arrayList;
        this.c = c;
    }

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
            convertView = inflater.inflate(R.layout.info_list, null);
        }
        convertView.setPadding(10,10,10,10);
        TextView t1 = (TextView) convertView.findViewById(R.id.infoNick);
        TextView t2 = (TextView) convertView.findViewById(R.id.infoTime);
        Info one;
        one = arrayList.get(position);
        t1.setText(one.Nick);
        t2.setText(one.Time);

        return convertView;
    }
}
