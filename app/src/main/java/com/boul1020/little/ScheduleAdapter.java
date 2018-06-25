package com.boul1020.little;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;

public class ScheduleAdapter extends BaseAdapter {

    Context context;
    ArrayList<Schedule> schedules;

    public ScheduleAdapter(Context context, ArrayList<Schedule> schedule) {
        this.context = context;
        this.schedules = schedule;
    }

    @Override
    public int getCount() { return schedules.size(); }

    @Override
    public Object getItem(int position) { return schedules.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) view = LayoutInflater.from(context).inflate(R.layout.schedule_dialog, viewGroup, false);


        EditText et = view.findViewById(R.id.dialog_Day);
        et.setText(schedules.get(position).content);

        return view;
    }

}
