//package com.example.park.togetherclass;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.ActionBar;
//import android.widget.Toast;
//
///**
// * Created by Park on 2017-06-04.
// */
//
//public class TabListener<T extends android.support.v4.app.Fragment> implements android.app.ActionBar.TabListener, ActionBar.TabListener {
//    private final Activity MyActivity;
//    private final String MyTag;
//    private final Class<T> MytClass;
//    private android.support.v4.app.Fragment MyFragment;
//
//    public TabListener(Activity activity, String tag, Class<T> tClass) {
//        MyActivity = activity;
//        MyTag = tag;
//        MytClass = tClass;
//        MyFragment = MyActivity.getFragmentManager().findFragmentByTag(MyTag);
//        if (MyFragment != null && !MyFragment.isDetached()) {
//            android.app.FragmentTransaction fragmentTransaction = MyActivity.getFragmentManager().beginTransaction();
//            fragmentTransaction.detach(MyFragment);
//            fragmentTransaction.commit();
//        }
//    }
//
//    @Override
//    public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//        if (MyFragment == null) {
//            MyFragment = android.support.v4.app.Fragment.instantiate(MyActivity, MytClass.getName(), null);
//            ft.add(android.R.id.content, MyFragment, MyTag);
//        } else {
//            ft.attach(MyFragment);
//        }
//    }
//
//    @Override
//    public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//        if (MyFragment != null) {
//            ft.detach(MyFragment);
//        }
//    }
//
//    @Override
//    public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
//        Toast.makeText(MyActivity, "onTabReselcted!", Toast.LENGTH_LONG).show();
//
//    }
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//        if (MyFragment == null) {
//            MyFragment = Fragment.instantiate(MyActivity, MytClass.getName(), null);
//            ft.add(android.R.id.content, MyFragment, MyTag);
//        } else {
//            ft.attach(MyFragment);
//        }
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//    }
//}
