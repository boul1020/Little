package com.boul1020.little;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MemoMakeActivity extends AppCompatActivity {
    EditText memo_edit = null;
    int mode;
    String filename; // 원본 파일 이름 (수정 모드일 경우에만 받아 오게 됨)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        memo_edit = findViewById(R.id.memo_edit);
        mode = getIntent().getIntExtra("mode", 1); // intent 값이 없으면, 무조건 신규 작성 모드로 인식하도록 함.
        // mode 값 - 1: 신규 작성, 2: 수정

        if (mode == 2) {
            filename = getIntent().getStringExtra("filename"); // 수정 모드일 경우에만 파일 이름을 받아옴.
            String content = getIntent().getStringExtra("content");
            memo_edit.setText(content);
        }

        if (Session.memoContent != null) {
            memo_edit.setText(Session.memoContent);
        }
    }

    public void clickSave(View v) {
        String memoData = memo_edit.getText().toString();
        TextFileManager textFileManager = new TextFileManager(this);
        if (mode == 2) textFileManager.delete(filename);
        textFileManager.save(TextFileManager.TYPE_MEMO, memoData, null);
        memo_edit.setText("");
        Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
    }

    public void clickList(View v) {
        String memoContent = memo_edit.getText().toString();
        if (memoContent != null && !memoContent.equals("")) {
            Session.memoContent = memoContent;
        }

        Intent intent = new Intent(this, MemoListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Session.memoContent = null;

        super.onBackPressed();
    }
}