package com.boul1020.little;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MemoListActivity extends AppCompatActivity {
    TextFileManager textFileManager = new TextFileManager(this);
    ArrayList<Memo> memos = new ArrayList<>();
    MemoAdapter adapter;
    ListView listView;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memolist);

        loadMemos();

        btn = findViewById(R.id.btn);

        listView = findViewById(R.id.List_view);
        adapter = new MemoAdapter(this, memos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MemoListActivity.this, position + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MemoListActivity.this, MemoMakeActivity.class);
                intent.putExtra("mode", 2); // Memo 액티비티를 여는 방식이 수정 모드인지 신규 작성 모드인지 구분. 1 : 신규 작성, 2: 수정
                intent.putExtra("filename", memos.get(position).filename);
                intent.putExtra("content", memos.get(position).content);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                // 1. 체크 표시 ON
                int count = listView.getChildCount();
                for (int i = 0; i < count; i++) {
                    View v = (View)listView.getChildAt(i);
                    CheckBox cb = v.findViewById(R.id.cb);
                    cb.setVisibility(View.VISIBLE);
                }

                // 2. 스크롤 시, 나머지 목록들에도 체크 표시가 나오도록 지정
                // 3. 버튼들이 보이게 지정
                if (!Memo.checkable) {
                    Memo.checkable = true;
                    btn.setText("DELETE");
                    ((CheckBox)view.findViewById(R.id.cb)).setChecked(true);
                    memos.get(position).checked = true;
                }

                return false;
            }
        });
    }



    public void clickBtn(View view) {
        if (Memo.checkable) { // 버튼의 글자가 DELETE로 바뀐 상태
            // 선택된 요소가 하나 이상 있으면, 전부 삭제
            int count = memos.size();

            for (int i = count - 1; i >= 0; i--) {
                if (memos.get(i).checked) {
                    // 1. 실제 저장되어 있는 파일 삭제
                    textFileManager.delete("memo\\" + memos.get(i).filename);

                    // 2. ArrayList에서 데이터를 삭제하고 Adapter에게 notify
                    memos.remove(i);
                    adapter.notifyDataSetChanged();

                    //https://github.com/dkim0419/SoundRecorder

//                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
//                    alertdialog.setMessage("삭제 알림창");
//
//                    alertdialog.setPositiveButton("취 소", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//                    alertdialog.setNegativeButton("삭 제", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    });
//
//                    AlertDialog alert = alertdialog.create();
//                    alert.setIcon(R.drawable.ic_delete_black_24dp);
//                    alert.setTitle("종 료");
//                    alert.show();
                }
            }

            if (memos.size() == 0) {
                Memo.checkable = false;
                btn.setText("ADD");
            }
        }
        else { // 버튼의 글자가 ADD인 상태
            Intent intent = new Intent(this, MemoMakeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (Memo.checkable) {
            // 스크롤 시 버튼 표시 해제
            Memo.checkable = false;
            btn.setText("ADD");

            // 체크 표시 OFF
            int count = listView.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = (View)listView.getChildAt(i);
                CheckBox cb = v.findViewById(R.id.cb);
                if (cb.isChecked()) cb.setChecked(false);
                cb.setVisibility(View.GONE);
            }
        }
        else {
            super.onBackPressed();
        }
    }

    void loadMemos() {
        String[] filenameList = fileList();

        for (String path : filenameList) {
            String foldername = "memo\\";
            if (!path.contains(foldername)) continue; // 경로가 "memo"라는 폴더 이름을 포함하지 않으면 건너뛰기

            Log.i("test", path);

            String filename = path.substring(foldername.length()); // 파일 이름
            String content = textFileManager.load(TextFileManager.TYPE_MEMO, filename); // 파일 내용

            // 날짜
            String date = filename.substring(0, 4) + "-" + filename.substring(4, 6) + "-" + filename.substring(6, 8);
            memos.add(new Memo(filename, content, date));
            Log.i("test", "memo-date : " + date);
        }
    }
}
