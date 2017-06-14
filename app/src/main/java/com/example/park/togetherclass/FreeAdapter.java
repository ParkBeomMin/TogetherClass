package com.example.park.togetherclass;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Park on 2017-05-30.
 */

public class FreeAdapter extends BaseAdapter {
    ArrayList<Free> arrayList = new ArrayList<Free>();
    Context c;
    int op = -1;

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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.free_list, null);
        }

        ImageView i1 = (ImageView) convertView.findViewById(R.id.freeimage);
        i1.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            i1.setClipToOutline(true);
        } // 이미지뷰 동그랗게

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.WholeList);
        TextView t1 = (TextView) convertView.findViewById(R.id.freetitle);
        TextView t2 = (TextView) convertView.findViewById(R.id.freenick);
        TextView t3 = (TextView) convertView.findViewById(R.id.freedate);
        TextView t4 = (TextView) convertView.findViewById(R.id.freeContent);
        LinearLayout l1 = (LinearLayout) convertView.findViewById(R.id.line1);
        Free one;
        one = arrayList.get(position);
        String Sub = one.Subject;
        if(Sub.equals("모앱")) {
            i1.setImageResource(R.drawable.mo);
        } else if(Sub.equals("컴구")) {
            i1.setImageResource(R.drawable.com);
        }else if(Sub.equals("디비")) {
            i1.setImageResource(R.drawable.di);
        }else if(Sub.equals("운영체제")) {
            i1.setImageResource(R.drawable.un);
        }else if(Sub.equals("데통")) {
            i1.setImageResource(R.drawable.de);
        }else if(Sub.equals("멀미")) {
            i1.setImageResource(R.drawable.mul);
        }else if(Sub.equals("알고리즘")) {
            i1.setImageResource(R.drawable.al);
        }
        t1.setText(one.Title);
        t2.setText(one.Nick);
        t3.setText(one.Date);
        t4.setText(one.Content);
        Log.d("BEOM9", "op : "+op +"posi : " + position);
        if (op == position) {
            t4.setVisibility(View.VISIBLE);
//            l1.setVisibility(View.VISIBLE);
        }
        else{
            t4.setVisibility(View.GONE);
//            l1.setVisibility(View.GONE);
        }
        return convertView;
    }
    Comparator<Free> Asc = new Comparator<Free>() {
        @Override
        public int compare(Free data, Free t1) {
            return t1.Date.compareTo(data.Date);
        }
    };

    final static int ASC = 0;

    public void setSort(int sortType) {
        if (sortType == ASC) {
            Collections.sort(arrayList, Asc);
            this.notifyDataSetChanged();
        }
    }
    public void setContent(int op) {
        this.op = op;
    }
}
