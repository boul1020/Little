package com.boul1020.little;

import android.graphics.Color;
import java.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class Saturday implements DayViewDecorator {

    private final Calendar calendar = Calendar.getInstance();

    public Saturday(int blue) {

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        day.copyTo(calendar);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDay == Calendar.SATURDAY;

    }

    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new ForegroundColorSpan(Color.BLUE));

    }
}