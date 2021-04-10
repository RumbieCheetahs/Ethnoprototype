package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ethnoprototype.data.AppDatabase;

import java.util.ArrayList;
import java.util.List;



public class VideoCategorisaton extends AppCompatActivity {

    VideoView videoView;
    Button btnPlay;
    ImageButton btnSave;
    TextView plantName;
    Intent intent;
    String latitude, path, longitude,date, time, id;;
    List<String> categories;
    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid_categorisaton);
        videoView = findViewById(R.id.videoView2);
        btnSave = findViewById(R.id.imageButtonSave);
        btnPlay = findViewById(R.id.buttonPlay);
        plantName = findViewById(R.id.textViewPlantName);
        appDatabase = AppDatabase.getAppDatabase(getBaseContext());
        categories = new ArrayList<>();

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(plantName.getText())){
                    plantName.setError("Field required");
                    plantName.requestFocus();
                }
                else if(categories.size() == 0){
                    Toast.makeText(getBaseContext(),"You have not selected any category",Toast.LENGTH_LONG).show();
                }
                else{

                }
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

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxAnimals:
                if (checked)
                    categories.add("Animals");
                else categories.remove("Animals");
                break;
            case R.id.checkBoxBadLuck:
                if (checked)
                    categories.add("Bad Luck");
                else categories.remove("Bad Luck");
                break;
            case R.id.checkBoxBadWitchcraft:
                if(checked)
                    categories.add("Bad Witchcraft");
                else categories.remove("Bad Witchcraft");
                break;
            case R.id.checkBoxBeauty:
                if (checked)
                    categories.add("Beauty");
                else categories.remove("Beauty");
                break;
            case R.id.checkBoxConstruction:
                if (checked)
                    categories.add("Construction");
                else categories.remove("Construction");
                break;
            case R.id.checkBoxFood:
                if(checked)
                    categories.add("Food");
                else categories.remove("Food");
                break;
            case R.id.checkBoxHousehold:
                if (checked)
                    categories.add("Household");
                else categories.remove("Household");
                break;
            case R.id.checkBoxNaturalMed:
                if (checked)
                    categories.add("Natural Medicine");
                else categories.remove("Natural Medicine");
                break;
            case R.id.checkBoxPerfume:
                if(checked)
                    categories.add("Perfume");
                else categories.remove("Perfume");
                break;
            case R.id.checkBoxPoisonous:
                if (checked)
                    categories.add("Poisonous");
                else categories.remove("Poisonous");
                break;
            case R.id.checkBoxRituals:
                if (checked)
                    categories.add("Rituals");
                else categories.remove("Rituals");
                break;
            case R.id.checkBoxGoodWitchcraft:
                if(checked)
                    categories.add("Good Witchcraft");
                else categories.remove("Good Witchcraft");
                break;

        }
    }
}