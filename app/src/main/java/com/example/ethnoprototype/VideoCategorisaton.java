package com.example.ethnoprototype;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.CategoryAndResource;
import com.example.ethnoprototype.data.CategoryAssignedResource;
import com.example.ethnoprototype.data.UnCategorizedImage;
import com.example.ethnoprototype.data.UnCategorizedVideo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;



public class VideoCategorisaton extends AppCompatActivity {

    VideoView videoView;
    Button btnPlay;
    ImageButton btnSave, plantImage;
    EditText plantName;
    Intent intent;
    String latitude, path, longitude,date, time, id;;
    List<String> categories;
    AppDatabase appDatabase;
    UnCategorizedVideo receivedVideo;
    String imagePath;
    Uri imageURI;
    public static int PICK_GALLERY_IMAGE = 1;

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
            setReceivedValues();
           // Toast.makeText(getBaseContext(),"Received Video Id "+receivedVideo.video_id,Toast.LENGTH_LONG).show();
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
                    //Load video and image resources
                    //Toast.makeText(getBaseContext(),"Processing else part",Toast.LENGTH_LONG).show();
//                    List<UnCategorizedVideo> videoList = appDatabase.videoDAO().loadAllByIds(new int[]{Integer.parseInt(id)});
//                    UnCategorizedVideo  unCategorizedVideo = videoList.get(0);
                    receivedVideo.category = true;
                    //Get image record
                    File file = FileUtils.getFile(getBaseContext(), imageURI);
                    UnCategorizedImage image = appDatabase.imageDAO().getImageFromPath(file.getAbsolutePath());
                    //Assign category resource video and get the id back
                    CategoryAssignedResource categoryAssignedResource = new CategoryAssignedResource();
//                    categoryAssignedResource.imageId = null;
                    categoryAssignedResource.plantName = plantName.getText().toString();
                    categoryAssignedResource.videoId = receivedVideo;
                    categoryAssignedResource.imageId = image;

                    long id = appDatabase.assignedResourceDAO().insert(categoryAssignedResource);
                    Toast.makeText(getBaseContext(),"Returned Id "+id,Toast.LENGTH_LONG).show();
                    //Change the not categorized flag to category assigned
                    appDatabase.videoDAO().update(receivedVideo);

                    //Insert all the categories with the cat_id of this new resource
                    if(id!=0){
                        List<CategoryAndResource> categoryAndResourceList = new ArrayList<>();
                        CategoryAndResource [] categoryAndResources = new CategoryAndResource[categories.size()];
                        for(int i = 0; i <categoryAndResources.length; i++){
                            Log.d("Checkboxes","Selected category"+categories.get(i));
                            categoryAndResources[i] = new CategoryAndResource(categories.get(i), Integer.parseInt(Long.toString(id)));
                        }
                        appDatabase.categoryAndResourceDAO().insertAll(categoryAndResources);
                        Toast.makeText(getBaseContext(),"Categories successfully assigned",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(VideoCategorisaton.this,Second_screen.class));
                        finish();
                    }


                }
            }
        });

        plantImage = (ImageButton) findViewById(R.id.imageButton2);
        plantImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFromGalleryIntent = new Intent();
                chooseFromGalleryIntent.setType("image/*");
                chooseFromGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                chooseFromGalleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                chooseFromGalleryIntent.putExtra("return-data", true);
                String[] mimeTypes = new String[]{"image/jpeg", "image/png"};  // 3
                chooseFromGalleryIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(Intent.createChooser(chooseFromGalleryIntent, "Select Image"), PICK_GALLERY_IMAGE);
            }
        });



    }

    private void setReceivedValues() {

        path = intent.getStringExtra("path");

        latitude = intent.getStringExtra("latitude");

        longitude = intent.getStringExtra("longitude");

        date = intent.getStringExtra("date");

        time = intent.getStringExtra("time");
       // Toast.makeText(getBaseContext(),"After receiving time "+time,Toast.LENGTH_LONG).show();

        id = intent.getStringExtra("id");

        receivedVideo = intent.getParcelableExtra("video");

      //  Toast.makeText(getBaseContext(),"After receiving "+receivedVideo.video_id,Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_GALLERY_IMAGE && resultCode == RESULT_OK){
            Uri sourceUri = data.getData();
//            File file = FileUtils.getFile(this, sourceUri);
            String path = sourceUri.getPath() ;// "/mnt/sdcard/FileName.mp3"
//            File file = new File(new URI(path));
//            File file = null;
//            try {
//                file = new File(new URI(path));
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//                Log.e("RARARARA",e.getMessage());
//            }
//            Toast.makeText(getBaseContext(),"IN ACTIVITY RESULT" +sourceUri,Toast.LENGTH_LONG).show();

            plantImage.setImageURI(sourceUri);
            imagePath = sourceUri.getPath();
            imageURI = sourceUri;
//                imageView.setImageBitmap(bmp1);

            //            InputStream inputStream = null;
//            try {
////                imagePath = file.getAbsolutePath();
////                Toast.makeText(getBaseContext(), "File path"+imagePath,Toast.LENGTH_LONG).show();
////                inputStream = new FileInputStream(file);
////
//////                Toast.makeText(getBaseContext(), "File path"+imagePath,Toast.LENGTH_LONG).show();
////                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
////                Bitmap bmp1 = Bitmap.createScaledBitmap(bitmap, 224, 224, false);
//                ImageView imageView = findViewById(R.id.imageView);
////                imageView.setImageBitmap(bmp1);
//                imageView.setImageURI(sourceUri);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Log.e("ERRRRRR",e.getMessage());
//            }
        }
    }
}