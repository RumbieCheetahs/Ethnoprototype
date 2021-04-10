package com.example.ethnoprototype;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.UnCategorizedImage;
import com.example.ethnoprototype.data.UnCategorizedVideo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class CaptureImageActivity extends AppCompatActivity {

    private String currentPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    LocationService locationService;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationService = new LocationService(CaptureImageActivity.this);
        db = AppDatabase.getAppDatabase(CaptureImageActivity.this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra( MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
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
//                Uri photoURI = Uri.parse(path);
                //Broadcast images
                //  File anotherFile = new File(path);
                Uri photoURI =  FileProvider.getUriForFile(this,
                        "com.example.ethnoprototype",
                        photoFile);

                Toast.makeText(getBaseContext(), "URI : "+ photoURI.toString(),Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            double[] coordinates = getLocation();
            if (coordinates != null) {
                double latitude = coordinates[0];
                double longitude = coordinates[1];
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
                    db.videoDAO().insertAll(video);
                    Toast.makeText(getBaseContext(), "Video successfully saved!", Toast.LENGTH_LONG).show();

                } else {
//                    Uri imageUri = data.getData();
                    //  Toast.makeText(getBaseContext(), "Image URI " + currentPath, Toast.LENGTH_LONG).show();
                    UnCategorizedImage image = new UnCategorizedImage();
                    image.imagePath = currentPath;
                    image.imageLatitude = latitude;
                    image.imageLongitude = longitude;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        image.imageDate = LocalDate.now().toString();
                        image.imageTime = LocalTime.now().toString();
                    }
                    db.imageDAO().insertAll(image);
                    addToGallery();
                    Toast.makeText(getBaseContext(), "Image successfully saved!", Toast.LENGTH_LONG).show();

                }
            }
            Toast.makeText(getApplicationContext(), " Resource saved but with no coordinates", Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private double[] getLocation(){
        if (locationService.canGetLocation()) {
            double longitude = locationService.getLongitude();
            double latitude = locationService.getLatitude();
            double[] location = new double[]{latitude,longitude};
            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            return location;
        } else {
            locationService.showSettingsAlert();
            return null;
        }
    }
}