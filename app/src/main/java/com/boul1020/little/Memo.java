package com.boul1020.little;

public class Memo {
    static boolean checkable = false; // 체크할 수 있는지 여부를 저장
    String filename;
    String content;
    String date;
    boolean checked; // MemoListActivity에서 체크가 되어 있는지를 저장하는 변수

    public Memo(String filename, String content, String date) {
        this.filename = filename;
        this.content = content;
        this.date = date;
    }
}
