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
 * Created by Park on 2017-06-02.
 */

public class MeetAdapter extends BaseAdapter {
    ArrayList<Meet> arrayList = new ArrayList<Meet>();
    Context c;

    public MeetAdapter(ArrayList<Meet> arrayList, Context c) {
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
            convertView = inflater.inflate(R.layout.meet_list, null);
        }
        Meet one;
        one = arrayList.get(position);

        ImageView i1 = (ImageView) convertView.findViewById(R.id.meetimage);
        TextView t1 = (TextView) convertView.findViewById(R.id.meetName);
        TextView t2 = (TextView) convertView.findViewById(R.id.meetRoom);TextView t3 = (TextView) convertView.findViewById(R.id.meetSite);
        TextView t4 = (TextView) convertView.findViewById(R.id.meetCall);
        TextView t5 = (TextView) convertView.findViewById(R.id.meetEmail);

        t1.setText(one.Name);
        t2.setText(one.Room);
        t3.setText(one.Site);
        t4.setText(one.Call);
        t5.setText(one.Email);

        return convertView;
    }
}
