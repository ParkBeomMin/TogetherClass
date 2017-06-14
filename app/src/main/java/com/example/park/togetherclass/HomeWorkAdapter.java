package com.example.park.togetherclass;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Created by Park on 2017-05-29.
 */

public class HomeWorkAdapter extends BaseAdapter {
    ArrayList<HomeWork> arrayList = new ArrayList<HomeWork>();
    Context c;
    int A;

    public HomeWorkAdapter(ArrayList<HomeWork> arrayList, Context c) {
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
            convertView = inflater.inflate(R.layout.homework_list, null);
        }
        ImageView i1 = (ImageView) convertView.findViewById(R.id.HLimage);
        TextView t1 = (TextView) convertView.findViewById(R.id.HLNameTv);
        TextView t2 = (TextView) convertView.findViewById(R.id.HLDdayTv);
        HomeWork one;
        one = arrayList.get(position);
        t1.setText(one.Name);
        switch (one.HomeWork) {
            case "과제":
                i1.setImageResource(R.drawable.homework);
                t1.setTextColor(Color.RED);
                break;
            case "공모전":
                i1.setImageResource(R.drawable.coworking);
                t1.setTextColor(Color.GREEN);
                break;
            case "개인일정":
                i1.setImageResource(R.drawable.person);
                t1.setTextColor(Color.BLUE);
                break;
        }

        String SetDday = one.DeadLine;
        String S[] = SetDday.split("-");
        int SetYear = Integer.parseInt(S[0]);
        int SetMonth = Integer.parseInt(S[1]);
        int SetDay = Integer.parseInt(S[2]);

        GregorianCalendar calendar = new GregorianCalendar();
        long currentTime = calendar.getTimeInMillis() / (1000 * 60 * 60 * 24);
        calendar.set(SetYear, SetMonth, SetDay);
        long DTime = calendar.getTimeInMillis() / (1000 * 60 * 60 * 24);
        int Dday = (int) (DTime - currentTime);
        A=Dday;
        t2.setText("D-day");
        t2.setTextColor(Color.RED);
        if (Dday > 0) {
            t2.setTextColor(Color.BLUE);
            t2.setText("D-" + Dday);
        } else if (Dday < 0) {
            t2.setTextColor(Color.BLUE);
            t2.setText("D+" + Math.abs(Dday));
        }
        return convertView;
    }

    Comparator<HomeWork> Asc = new Comparator<HomeWork>() {
        @Override
        public int compare(HomeWork data, HomeWork t1) {
            return data.DeadLine.compareTo(t1.DeadLine);
        }
    };

    final static int ASC = 0;

    public void setSort(int sortType) {
        if (sortType == ASC) {
            Collections.sort(arrayList, Asc);
            this.notifyDataSetChanged();
        }
    }
}
