package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.CategoryAndResource;
import com.example.ethnoprototype.data.CategoryAssignedResource;

import java.util.List;

public class ShowResourceActivity extends AppCompatActivity {

    Button btnNext, btnPrevious, btnPlay;
    VideoView video;
    ImageView imageView;
    TextView textView;
    AppDatabase db;
    int [] ids;
    int currentlyLoaded;
    CategoryAssignedResource currentResource;
    List<CategoryAssignedResource> resourceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resource);
        Intent intent = getIntent();
        if(intent != null){
            ids = intent.getIntArrayExtra("ids");
//            Toast.makeText(getBaseContext(),"Number of ids "+ids.length,Toast.LENGTH_LONG).show();
        }
        db = AppDatabase.getAppDatabase(getBaseContext());

        btnPlay = findViewById(R.id.buttonPlayResource);
        btnNext = findViewById(R.id.buttonNextResource);
        btnPrevious = findViewById(R.id.buttonPrevious);
        video = findViewById(R.id.videoView_Resource);
        imageView = findViewById(R.id.imageView_viewResource);
        textView = findViewById(R.id.textView2);

        resourceList = db.assignedResourceDAO().loadAllByIds(ids);
//        for(int i: ids){
//            Log.e("Number iripo iyi", " "+i);
//        }

        if(resourceList.size() != 0){

//            for(CategoryAssignedResource resource : resourceList){
//                Log.e("RESOURCE NAME"," "+resource.plantName);
//            }
//            Toast.makeText(getBaseContext(),"Resource list size" + resourceList.size(),Toast.LENGTH_LONG).show();
            currentResource = resourceList.get(0);
            currentlyLoaded = 0;
//            imageView.setImageURI();
            textView.setText(currentResource.plantName);
            Uri uri = Uri.parse(currentResource.videoId.path);
            video.setVideoURI(uri);
        }

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.start();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentlyLoaded!=0){
                    currentlyLoaded-=1;
                    currentResource = resourceList.get(currentlyLoaded);
                    textView.setText(currentResource.plantName);
                    Uri uri = Uri.parse(currentResource.videoId.path);
                    video.setVideoURI(uri);
                }
                else {
                    Toast.makeText(getBaseContext(),"You have reached the first media",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentlyLoaded!=resourceList.size()-1){
                    currentlyLoaded+=1;
                    currentResource = resourceList.get(currentlyLoaded);
                    textView.setText(currentResource.plantName);
                    Uri uri = Uri.parse(currentResource.videoId.path);
                    video.setVideoURI(uri);
                }
                else {
                    Toast.makeText(getBaseContext(),"You have reached the last media",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}