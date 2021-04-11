package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.UnCategorizedImage;
import com.example.ethnoprototype.data.UnCategorizedVideo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Second_screen extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    String currentPath;
    LocationService locationService;
    ImageButton btnAssignCategories, btnCaptureImage, btnCaptureVideo;
    Button btnCategoriesList;

    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);

        btnAssignCategories = findViewById(R.id.btn_categories);
        btnCaptureImage = findViewById(R.id.btn_camera);
        btnCaptureVideo = findViewById(R.id.btn_video);
        //btnCategoriesList = findViewById(R.id.button_category_list);
        btnAssignCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Second_screen.this, UncategorisedItemsActivity.class));
            }
        });

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(Second_screen.this,CaptureImageActivity.class));
               dispatchTakePictureIntent();
            }
        });

        btnCaptureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });

//        btnCategoriesList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(Second_screen.this, CategoryListActivity.class));
//            }
//        });
        locationService = new LocationService(Second_screen.this);
        db = AppDatabase.getAppDatabase(Second_screen.this);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // takePictureIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
           // startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(this,
                        "Photo file can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            String path = photoFile.getAbsolutePath();
            // Continue only if the File was successfully created
            if (photoFile != null) {

               Uri photoURI =  FileProvider.getUriForFile(this,
                       BuildConfig.APPLICATION_ID + ".provider",
                       photoFile);

//                Toast.makeText(getBaseContext(), "URI : "+ photoURI.toString(),Toast.LENGTH_LONG).show();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void addToGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPath);
        Uri picUri = Uri.fromFile(f);
        galleryIntent.setData(picUri);
        this.sendBroadcast(galleryIntent);
    }
    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            File videoFile = null;
            try {
                videoFile = createVideoFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (videoFile != null) {
                Uri videoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        videoFile);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
//                Toast.makeText(getBaseContext(), "URI : "+ videoURI.toString(),Toast.LENGTH_LONG).show();
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            double[] coordinates = getLocation();
            double latitude = 0.0;
            double longitude = 0.0;

            if (coordinates != null) {
                latitude = coordinates[0];
                longitude = coordinates[1];
            } else {
                Toast.makeText(getApplicationContext(), " Resource will be saved but it has no coordinates", Toast.LENGTH_LONG).show();
            }
                if (requestCode == REQUEST_VIDEO_CAPTURE) {
                 //   Uri videoUri = data.getData();
                   // Toast.makeText(getBaseContext(), "Video URI " + currentPath, Toast.LENGTH_LONG).show();
                    UnCategorizedVideo video = new UnCategorizedVideo();
                    video.path = currentPath;
                    video.latitude = latitude;
                    video.longitude = longitude;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        video.date = LocalDate.now().toString();
                        video.time = LocalTime.now().toString();
                    }
                    else {
                        video.date = new SimpleDateFormat("ddMMyyyy").format(new Date());
                        video.time = new SimpleDateFormat("HHmmss").format(new Date());
                    }
                    db.videoDAO().insertAll(video);
                    Toast.makeText(getBaseContext(), "Video successfully saved!", Toast.LENGTH_LONG).show();

                } else {

                    UnCategorizedImage image = new UnCategorizedImage();
                    image.imagePath = currentPath;
                    image.imageLatitude = latitude;
                    image.imageLongitude = longitude;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        image.imageDate = LocalDate.now().toString();
                        image.imageTime = LocalTime.now().toString();
                    }
                    else {
                        image.imageDate = new SimpleDateFormat("ddMMyyyy").format(new Date());
                        image.imageTime = new SimpleDateFormat("HHmmss").format(new Date());
                    }
                    db.imageDAO().insertAll(image);
                    addToGallery();
                    Toast.makeText(getBaseContext(), "Image successfully saved!", Toast.LENGTH_LONG).show();
                }
//            }else {
//                Toast.makeText(getApplicationContext(), " Resource could not be saved it has no coordinates", Toast.LENGTH_LONG).show();
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPath = image.getAbsolutePath();
        return image;
    }

    private File createVideoFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MP4_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File video = File.createTempFile(
                imageFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPath = video.getAbsolutePath();
        return video;
    }

    private double[] getLocation(){
        if (locationService.canGetLocation()) {
            double longitude = locationService.getLongitude();
            double latitude = locationService.getLatitude();
            //            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            return new double[]{latitude,longitude};
        } else {
         //   locationService.showSettingsAlert();
            return null;
        }
    }
}