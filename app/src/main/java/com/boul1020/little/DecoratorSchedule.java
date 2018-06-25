package com.boul1020.little;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;

public class DecoratorSchedule implements DayViewDecorator {
    Context context;
    ArrayList<CalendarDay> dayList;
    Resources res;

    public DecoratorSchedule(Context context, ArrayList<CalendarDay> dayList) {
        this.context = context;
        this.dayList = dayList;
        this.res = context.getResources();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        for (CalendarDay t : dayList) {
            if (t.getDate().toString().equals(day.getDate().toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) { // 메서드에 무조건 무언가 정의를 내려야 shouldDecorate가 발동함.
        view.addSpan(new ForegroundColorSpan(Color.GREEN));

        // TODO : R.drawable.img_test 대신 원하는 배경으로 변경
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.img_test);

        view.setBackgroundDrawable(new BitmapDrawable(res, bitmap));
    }
}
