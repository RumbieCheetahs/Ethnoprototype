package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Second_screen extends AppCompatActivity {

    ImageButton btnAssignCategories, btnCaptureImage, btnCaptureVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        btnAssignCategories = findViewById(R.id.btn_categories);
        btnCaptureImage = findViewById(R.id.btn_camera);
        btnCaptureVideo = findViewById(R.id.btn_video);

        btnAssignCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Second_screen.this, UncategorisedItemsActivity.class));
            }
        });

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCaptureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}