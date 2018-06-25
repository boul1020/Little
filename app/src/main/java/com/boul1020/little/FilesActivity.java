package com.boul1020.little;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FilesActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);


    }

    public void clickMemos(View v){

        Intent intent = new Intent(this, MemoListActivity.class);
        startActivity(intent);

    }
}
