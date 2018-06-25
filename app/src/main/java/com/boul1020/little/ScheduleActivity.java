package com.boul1020.little;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {
    TextFileManager textFileManager;

    MaterialCalendarView calendarView;
    DayViewDecorator oneDayDecorator;
    ArrayList<Schedule> schedules = new ArrayList<>();
    ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        textFileManager = new TextFileManager(this);
        adapter = new ScheduleAdapter(this, schedules);
        calendarView = findViewById(R.id.calendarView);

        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from(2018, 0, 1))
                .setMaximumDate(CalendarDay.from(2030, 11, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                loadSchedule();
            }
        });

        oneDayDecorator = new DecoratorSunday();

        calendarView.addDecorators(new Sunday(Color.RED), new Saturday(Color.BLUE), oneDayDecorator);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Intent intent = new Intent(ScheduleActivity.this, ScheduleMakeActivity.class);
                intent.putExtra("Year", date.getYear());
                intent.putExtra("Month", date.getMonth()); // 월 값은 0부터 시작하는 것에 주의
                intent.putExtra("Day", date.getDay());

                startActivity(intent);


//                AlertDialog alerDialog = new AlertDialog.Builder(ScheduleActivity.this).setView(R.layout.schedule_dialog).create();
//                alerDialog.setTitle(schedules.);
//                alerDialog.setIcon(android.R.drawable.ic_dialog_alert);
//                alerDialog.setItems(, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int which) {
//                Toast t= Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT);
//                t.show();
            }
        });
                // positiveButton (일정 추가버튼) 클릭 시에 날짜, 시간, 메시지 받아서
            }
            //https://hashcode.co.kr/questions/4935/android-material-calendar%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-listview-%EC%9D%BC%EC%A0%95-%EC%B6%94%EA%B0%80 AlertDialog창
            //http://79chemi.net/115 상단 팝업창


    @Override
    protected void onResume() {
        super.onResume();

        loadSchedule();
    }

    void loadSchedule() {
        String[] filenameList = fileList();

        CalendarDay calendarDate = calendarView.getCurrentDate(); // 달력에 지정된 현재 날짜
        int calendarYear = calendarDate.getYear();
        int calendarMonth = calendarDate.getMonth() + 1;
        int calendarDay = calendarDate.getDay();

        ArrayList<CalendarDay> dayList = new ArrayList<>();

        for (String path : filenameList) {
            String foldername = "schedule\\";
            if (!path.contains(foldername)) continue; // 경로가 "schedule"라는 폴더 이름을 포함하지 않으면 건너뛰기

            String filename = path.substring(foldername.length());
            int year = Integer.parseInt(filename.substring(0, 4));
            int month = Integer.parseInt(filename.substring(4, 6));
            int day = Integer.parseInt(filename.substring(6, 8));

            if (year != calendarYear || month != calendarMonth) continue;

            dayList.add(CalendarDay.from(year, month - 1, day));
        }

        calendarView.addDecorator(new DecoratorSchedule(this, dayList));

        // TODO : 체크된 날짜의 경우, 새로 작성이 아닌 내용 확인을 위한 액티비티로 전환되도록 해야 함.


    }

//    void deleteAllSchedule() { // 임시 메서드
//        String[] filenameList = fileList();
//
//        for (String path : filenameList) {
//            String foldername = "schedule\\";
//            if (!path.contains(foldername)) continue; // 경로가 "schedule"라는 폴더 이름을 포함하지 않으면 건너뛰기
//            textFileManager.delete(path);
//        }
//    }
}




