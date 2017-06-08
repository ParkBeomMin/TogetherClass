package com.example.park.togetherclass;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Park on 2017-06-07.
 */

public class RecordAdapter extends BaseAdapter {
    ArrayList<Record> arrayList = new ArrayList<>();
    Context c;
    ImageView i1;
    int op;
    public RecordAdapter(ArrayList<Record> arrayList, Context c) {
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.free_list, null);
        }
         i1 = (ImageView) convertView.findViewById(R.id.freeimage);
        TextView t1 = (TextView) convertView.findViewById(R.id.freetitle);
        TextView t2 = (TextView) convertView.findViewById(R.id.freenick);
        t2.setVisibility(View.GONE);
        TextView t3 = (TextView) convertView.findViewById(R.id.freedate);
        t3.setVisibility(View.GONE);
        TextView t4 = (TextView) convertView.findViewById(R.id.NickText);
        t4.setVisibility(View.GONE);
        Record one;
        one = arrayList.get(position);
        i1.setImageResource(R.drawable.play);
        if(op==position) {
            i1.setImageResource(R.drawable.pause);
        }else {
            i1.setImageResource(R.drawable.play);
        }
        t1.setText(one.Name);

        t1.setPadding(0,30,0,0);
        return convertView;
    }


    public void setImage(int op) {
        this.op = op;
//        if(i){
//            i1.setImageResource(R.drawable.play);
//        }else{
//            i1.setImageResource(R.drawable.pause);
//        }
    }
}
