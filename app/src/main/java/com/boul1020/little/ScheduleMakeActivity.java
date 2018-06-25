package com.boul1020.little;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ScheduleMakeActivity extends AppCompatActivity {
    TextFileManager textFileManager;

    EditText editDate;
    EditText editTitle;
    EditText editContent;

    int year;
    int month;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_make);

        textFileManager = new TextFileManager(this);

        editDate = findViewById(R.id.edit_date);
        editTitle = findViewById(R.id.edit_title);
        editContent = findViewById(R.id.edit_content);



        Intent intent = getIntent();

        year = intent.getIntExtra("Year", 2018);
        month = intent.getIntExtra("Month", 1) + 1;
        day = intent.getIntExtra("Day", 1);

        editDate.setText(String.format("%d년 %d월 %d일", year, month, day));
    }

    public void clickSave(View view) {
        // 다음과 같은 방식으로 내부저장소에 파일 저장
        /*
            <date>날짜</date>
            <title>제목</title>
            <content>내용</content>
         */

        String date = String.format("%04d%02d%02d", year, month, day);
        String title = editTitle.getText().toString();
        String content = editContent.getText().toString();
        if (title.equals("")) {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (content.equals("")) {
            Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        String strDate = "<date>" + date + "</date>";
        String strTitle = "<title>" + title + "</title>";
        String strContent = "<content>" + content + "</content>";

        textFileManager.save(TextFileManager.TYPE_SCHEDULE, strDate + "\n" + strTitle + "\n" + strContent + "\n", date + ".txt");

        finish(); // 액티비티 종료
    }
}
