package com.example.park.togetherclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Park on 2017-06-02.
 */

public class NoticeAdapter extends BaseAdapter {
    ArrayList<Notice> arrayList = new ArrayList<Notice>();
    Context c;

    public NoticeAdapter(ArrayList<Notice> arrayList, Context c) {
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
            convertView = inflater.inflate(R.layout.free_list, null);
        }
        TextView t1 = (TextView) convertView.findViewById(R.id.freetitle);
        TextView t2 = (TextView) convertView.findViewById(R.id.freenick);
        TextView t3 = (TextView) convertView.findViewById(R.id.freedate);
        TextView t4 = (TextView) convertView.findViewById(R.id.freeContent);

        t2.setVisibility(View.GONE);

        Notice one;
        one = arrayList.get(position);
        t1.setText(one.Title);
        t3.setText(one.Date);


        return  convertView;
    }
}