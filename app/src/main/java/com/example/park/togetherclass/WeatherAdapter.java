package com.example.park.togetherclass;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Park on 2017-06-09.
 */

public class WeatherAdapter extends BaseAdapter {
    ArrayList<Weather> arrayList = new ArrayList<Weather>();
    Context c;

    public WeatherAdapter(ArrayList<Weather> arrayList, Context c) {
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
            convertView = inflater.inflate(R.layout.weather, null);
        }
        TextView t1 = (TextView) convertView.findViewById(R.id.weatherTv1);
        TextView t2 = (TextView) convertView.findViewById(R.id.weatherTv2);
        ImageView i1 = (ImageView) convertView.findViewById(R.id.weatherImage);

        Weather one;
        one = arrayList.get(position);
        String w1 = java.util.Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + Integer.parseInt(one.getDay()) + "일 " +
                one.getHour() + "시 " +
                one.getTemp() + "도 "+ one.getWfKor();
        String w2 = "강수확률 " +
        one.getPop() + "%";

                t1.setText(w1);
        t2.setText(w2);
        t1.setTextColor(Color.WHITE);
        t2.setTextColor(Color.WHITE);
        if(one.getWfKor().equals("맑음")) {
            i1.setImageResource(R.drawable.sun);
        }else if(one.getWfKor().equals("구름 조금")) {
            i1.setImageResource(R.drawable.cloud_little);
        }else if(one.getWfKor().equals("구름 많음")) {
            i1.setImageResource(R.drawable.cloud);
        }else if(one.getWfKor().equals("비")) {
            i1.setImageResource(R.drawable.rain);
        }else if(one.getWfKor().equals("눈")) {
            i1.setImageResource(R.drawable.snow);
        }

        return convertView;
    }
}
