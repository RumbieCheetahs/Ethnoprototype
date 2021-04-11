package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.CategoryAndResource;
import com.example.ethnoprototype.data.CategoryAssignedResource;

import java.util.List;

public class ShowResourceActivity extends AppCompatActivity {

    AppDatabase db;
    int [] ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_resource);
        Intent intent = getIntent();
        if(intent != null){
            ids = intent.getIntArrayExtra("ids");
        }
        db = AppDatabase.getAppDatabase(getBaseContext());

        List<CategoryAssignedResource> resourceList = db.assignedResourceDAO().loadAllByIds(ids);
    }
}