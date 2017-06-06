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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.meet_list, null);
        }
        Meet one;
        one = arrayList.get(position);

        ImageView i1 = (ImageView) convertView.findViewById(R.id.meetimage);
        TextView t1 = (TextView) convertView.findViewById(R.id.meetName);
        TextView t2 = (TextView) convertView.findViewById(R.id.meetRoom);
        TextView t3 = (TextView) convertView.findViewById(R.id.meetSite);
        TextView t4 = (TextView) convertView.findViewById(R.id.meetCall);
        TextView t5 = (TextView) convertView.findViewById(R.id.meetEmail);

        t1.setText(one.Name);
        t2.setText(one.Room);
        t3.setText(one.Site);
        t4.setText(one.Call);
        t5.setText(one.Email);
setImage(t1.getText().toString(), i1);
        return convertView;
    }


    void setImage(String Name, ImageView image) {
        switch (Name) {
            case "조성현 교수님":
                image.setImageResource(R.drawable.jo_sung_hyun);
                break;
            case "도경구 교수님":
                image.setImageResource(R.drawable.do_kyung_gu);
                break;
            case "강경태 교수님":
                image.setImageResource(R.drawable.kang_kyung_tae);
                break;
            case "김정선 교수님":
                image.setImageResource(R.drawable.kim_jung_sun);
                break;
            case "김태형 교수님":
                image.setImageResource(R.drawable.kim_tae_hyung);
                break;
            case "김영훈 교수님":
                image.setImageResource(R.drawable.kim_yung_hun);
                break;
            case "이동호 교수님":
                image.setImageResource(R.drawable.lee_dong_ho);
                break;
            case "이정규 교수님":
                image.setImageResource(R.drawable.lee_jung_gyu);
                break;
            case "이석복 교수님":
                image.setImageResource(R.drawable.lee_suk_bok);
                break;
            case "마상백 교수님":
                image.setImageResource(R.drawable.ma_sang_back);
                break;
            case "문영식 교수님":
                image.setImageResource(R.drawable.moon_yung_sik);
                break;
            case "오희국 교수님":
                image.setImageResource(R.drawable.oh_hee_guk);
                break;
            case "박성주 교수님":
                image.setImageResource(R.drawable.park_sung_ju);
                break;
            case "Scott Uk-Jin Lee 교수님":
                image.setImageResource(R.drawable.scott);
                break;
            case "윤종원 교수님":
                image.setImageResource(R.drawable.yoon_jong_won);
                break;
        }
    }
}
