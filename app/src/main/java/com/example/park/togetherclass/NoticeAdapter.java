package com.example.park.togetherclass;

import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ImageView i1 = (ImageView) convertView.findViewById(R.id.freeimage);
        i1.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            i1.setClipToOutline(true);
        } // 이미지뷰 동그랗게

        TextView t1 = (TextView) convertView.findViewById(R.id.freetitle);
        TextView t2 = (TextView) convertView.findViewById(R.id.freenick);
        TextView t3 = (TextView) convertView.findViewById(R.id.freedate);
        TextView t4 = (TextView) convertView.findViewById(R.id.freeContent);

        Notice one;
        one = arrayList.get(position);
        t1.setText(one.Title);
        t3.setText(one.Date);
        t2.setText(one.Nick);
        if(one.Subject.equals("모앱")) {
            i1.setImageResource(R.drawable.mo);
        }
        else if(one.Subject.equals("컴구")) {
            i1.setImageResource(R.drawable.com);
        }
        else if(one.Subject.equals("디비")) {
            i1.setImageResource(R.drawable.di);
        }
        else if(one.Subject.equals("데통")) {
            i1.setImageResource(R.drawable.de);
        }
        else if(one.Subject.equals("운영체제")) {
            i1.setImageResource(R.drawable.un);
        }
        else if(one.Subject.equals("알고리즘")) {
            i1.setImageResource(R.drawable.al);
        }
        else if(one.Subject.equals("멀미")) {
            i1.setImageResource(R.drawable.mul);
        }

        return  convertView;
    }
}
