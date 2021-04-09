package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class vid_categorisaton extends AppCompatActivity {

    VideoView videoView;
    Button btnPlay;
    Intent intent;
    String latitude, path, longitude,date, time, id;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_categorisaton);
        videoView = findViewById(R.id.videoView2);

        btnPlay = findViewById(R.id.buttonPlay);
        intent = getIntent();
        if(intent!= null) {
//            isReceivedIntent = true;
            setReceivedValues();
        }
        else {
            btnPlay.setVisibility(View.GONE);
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(path);
                videoView.setVideoURI(uri);
                videoView.start();
            }
        });
    }

    private void setReceivedValues() {

        path = intent.getStringExtra("path");

        latitude = intent.getStringExtra("latitude");

        longitude = intent.getStringExtra("longitude");

        date = intent.getStringExtra("date");

        time = intent.getStringExtra("time");

        id = intent.getStringExtra("id");
    }
}