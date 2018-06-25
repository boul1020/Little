package com.boul1020.little;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;

public class RecorderActivity extends Activity {

    static final String RECORDED_FILE = "/sdcard/recorded.mp4";

    MediaPlayer player;
    MediaRecorder recorder;


    Button recordBtn, recordStopBtn, playBtn, playStopBtn;

    int playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        recordBtn = findViewById(R.id.recordBtn);
        recordStopBtn =findViewById(R.id.recordStopBtn);
        playBtn = findViewById(R.id.playBtn);
        playStopBtn = findViewById(R.id.playStopBtn);

        recordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (recorder != null){
                    recorder.stop();
                    recorder.release();
                    recorder = null;

                }

                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                recorder.setOutputFile(RECORDED_FILE);

            try{

                Toast.makeText(RecorderActivity.this, "녹음 시작합니다.", Toast.LENGTH_SHORT).show();
                recorder.prepare();
                recorder.start();

            }catch (Exception e){

                Log.e("SampleAudioRecorder", "Exception : ", e);

            }
            }
        });

        recordStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (recorder == null){ return; }

                recorder.stop();
                recorder.release();
                recorder = null;

                Toast.makeText(RecorderActivity.this, "녹음이 중지 되었습니다.", Toast.LENGTH_SHORT).show();

            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    playAudio(RECORDED_FILE);
                    Toast.makeText(getApplicationContext(), "재생 시작 되었습니다.", Toast.LENGTH_SHORT).show();

                }catch (Exception e){

                    e.printStackTrace();

                }
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                try{

                    playAudio(RECORDED_FILE);
                    Toast.makeText(RecorderActivity.this, "재생 시작되었습니다.", Toast.LENGTH_SHORT).show();

                } catch(Exception e){

                    e.printStackTrace();
                }
            }
        });

        playStopBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                if(player != null){
                    playbackPosition = player.getCurrentPosition();
                    player.pause();
                    Toast.makeText(RecorderActivity.this, "재생이 중지 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void playAudio(String url) throws Exception{

        killMediaPlayer();
        player = new MediaPlayer();
        player.setDataSource(url);
        player.prepare();
        player.start();

    }


    protected void onDestroy() {

            super.onDestroy();
            killMediaPlayer();

        }

        private void killMediaPlayer() {

            if(player != null){
                try {
                    player.release();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

        }

        protected void onPause(){

            if(recorder != null){
                recorder.release();
                recorder = null;
            }

            if (player != null){
                player.release();
                player = null;
            }

            super.onPause();

        }
    }
