package com.example.fittrack2;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;

import java.util.ArrayList;

public class NavAdapter extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter {

    ArrayList<AppPage> appPageArrayList;

    public NavAdapter(ArrayList<AppPage> appPageArrayList) {
        this.appPageArrayList = appPageArrayList;
    }

    @Override
    public CharSequence getItemText(int pos) {
        return appPageArrayList.get(pos).getTitle();
    }

    @Override
    public Drawable getItemDrawable(int pos) {
        return appPageArrayList.get(pos).getIcon();
    }

    @Override
    public int getCount() {
        return appPageArrayList.size();
    }
}
