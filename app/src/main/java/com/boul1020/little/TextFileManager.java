package com.boul1020.little;


import android.content.Context;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TextFileManager {
    public static final int TYPE_MEMO = 1;
    public static final int TYPE_SCHEDULE = 2;

    Context context;
    GregorianCalendar calendar;

    public TextFileManager(Context context) {
        this.context = context;
        this.calendar = new GregorianCalendar();
    }

    public void save(int type, String data, @Nullable String filename) {
        if (data == null || data.equals("")) return;

        if (filename == null) {
            int year = calendar.get(Calendar.YEAR);         // 연도를 리턴
            int month = calendar.get(Calendar.MONTH) + 1;   // 월을 리턴
            int date = calendar.get(Calendar.DATE);         // 일을 리턴
            int hour = calendar.get(Calendar.HOUR_OF_DAY);  // 시를 리턴
            int min = calendar.get(Calendar.MINUTE);        // 분을 리턴
            int sec = calendar.get(Calendar.SECOND);        // 초를 리턴

            filename = String.format("%4d%02d%02d%02d%02d%02d.txt", year, month, date, hour, min, sec);
        }

        FileOutputStream fos = null;
        try {
            if (type == TYPE_MEMO) {
                fos = context.openFileOutput("memo\\" + filename, Context.MODE_PRIVATE);
            }
            else if (type == TYPE_SCHEDULE) {
                fos = context.openFileOutput("schedule\\" + filename, Context.MODE_PRIVATE);
            }

            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String load(int type, String filename) {
        StringBuffer buffer = new StringBuffer("");

        try {
            FileInputStream fis = null;
            if (type == TYPE_MEMO) {
                fis = context.openFileInput("memo\\" + filename);
            }
            else if (type == TYPE_SCHEDULE) {
                fis = context.openFileInput("schedule\\" + filename);
            }
            InputStreamReader reader = new InputStreamReader(fis);

            int n;
            while ((n = reader.read()) != -1) {
                buffer.append(Character.toChars(n));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public void delete(String filename) {
        context.deleteFile(filename);
    }
}
