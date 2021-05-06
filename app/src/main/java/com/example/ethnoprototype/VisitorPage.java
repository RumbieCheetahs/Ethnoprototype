package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.MapFragment;

public class VisitorPage extends AppCompatActivity {

    Button btnEthnoBotanicalWalk, btnSearchByName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_page);


        btnEthnoBotanicalWalk = findViewById(R.id.searchbycategory);

        btnEthnoBotanicalWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisitorPage.this, MapsFragment.class));
            }
        });
    }
}