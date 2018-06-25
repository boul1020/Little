package com.boul1020.little;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MemoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Memo> memos;

    public MemoAdapter(Context context, ArrayList<Memo> memos) {
        this.context = context;
        this.memos = memos;
    }

    @Override
    public int getCount() {
        return memos.size();
    }

    @Override
    public Object getItem(int position) {
        return memos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        // View 재활용
        if (view == null) view = LayoutInflater.from(context).inflate(R.layout.menu_memolist, viewGroup, false);

        TextView tv = view.findViewById(R.id.tv_content);
        tv.setText(memos.get(position).content);

        CheckBox cb = view.findViewById(R.id.cb);
        if (Memo.checkable) cb.setVisibility(View.VISIBLE);
        else cb.setVisibility(View.GONE);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    memos.get(position).checked = true;
                }
                else {
                    memos.get(position).checked = false;
                }
            }
        });

        if (memos.get(position).checked) cb.setChecked(true);

        return view;
    }
}
