package com.example.park.togetherclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Park on 2017-05-30.
 */

public class FreeAdapter extends BaseAdapter {
    ArrayList<Free> arrayList = new ArrayList<Free>();
    Context c;
    int op;

    public FreeAdapter(ArrayList<Free> arrayList, Context c) {
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
        if(convertView == null)
        convertView = inflater.inflate(R.layout.free_list, null);
        TextView t1 = (TextView) convertView.findViewById(R.id.freetitle);
        TextView t2 = (TextView) convertView.findViewById(R.id.freenick);
        TextView t3 = (TextView) convertView.findViewById(R.id.freedate);
        TextView t4 = (TextView) convertView.findViewById(R.id.freeContent);
        LinearLayout l1 = (LinearLayout) convertView.findViewById(R.id.line1);
        Free one;
        one = arrayList.get(position);
        t1.setText(one.Title);
        t2.setText(one.Nick);
        t3.setText(one.Date);
        t4.setText(one.Content);
        if(op == 1) {
            t4.setVisibility(View.VISIBLE);
            l1.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    public void setContent(int op) {
        this.op = op;
    }
}
