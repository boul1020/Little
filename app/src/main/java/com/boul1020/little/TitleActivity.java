package com.boul1020.little;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void clickMemo(View v){

        Intent intent = new Intent(this, MemoMakeActivity.class);
        startActivity(intent);

    }

    public void clickRecord(View v){

        Intent intent = new Intent(this, RecorderActivity.class);
        startActivity(intent);

    }

    public void clickDocument(View v){

        Intent intent = new Intent(getApplicationContext(), FilesActivity.class);
        startActivity(intent);
    }

    public void clickSchedule(View v){

        Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
        startActivity(intent);
    }

    public void clickExit2(View v){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setMessage("종료 알림창");

        alertdialog.setPositiveButton("취 소", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertdialog.setNegativeButton("종 료", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.setIcon(R.drawable.x);
        alert.setTitle("종 료");
        alert.show();

    }
}
