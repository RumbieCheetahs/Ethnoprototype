package com.example.ethnoprototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
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

    AppDatabase db;
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
                dispatchTakePictureIntent();
            }
        });

        btnCaptureVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });
        locationService = new LocationService(Second_screen.this);
        db = AppDatabase.getAppDatabase(Second_screen.this);

    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            String path = photoFile.getAbsolutePath();
            // Continue only if the File was successfully created
            if (photoFile != null) {
//                Uri photoURI = Uri.parse(path);
                //Broadcast images
                File anotherFile = new File(path);
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

               Uri photoURI    =        FileProvider.getUriForFile(this,
                        "com.example.ethnoprototype",
                        anotherFile);
                mediaScanIntent.setData(photoURI);
                this.sendBroadcast(mediaScanIntent);
                Toast.makeText(getBaseContext(), "URI : "+ photoURI.toString(),Toast.LENGTH_LONG).show();

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
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
                        "com.example.ethnoprototype",
                        videoFile);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
//                Toast.makeText(getBaseContext(), "URI : "+ videoURI.toString(),Toast.LENGTH_LONG).show();
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
            }
        }
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
                    Toast.makeText(getBaseContext(), "Video URI " + currentPath, Toast.LENGTH_LONG).show();
                    UnCategorizedVideo video = new UnCategorizedVideo();
                    video.path = currentPath;
                    video.latitude = latitude;
                    video.longitude = longitude;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        video.date = LocalDate.now().toString();
                        video.time = LocalTime.now().toString();
                    }
                    db.videoDAO().insertAll(video);
                } else {
//                    Uri imageUri = data.getData();
                    Toast.makeText(getBaseContext(), "Image URI " + currentPath, Toast.LENGTH_LONG).show();
                    UnCategorizedImage image = new UnCategorizedImage();
                    image.imagePath = currentPath;
                    image.imageLatitude = latitude;
                    image.imageLongitude = longitude;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        image.imageDate = LocalDate.now().toString();
                        image.imageTime = LocalTime.now().toString();
                    }
                    db.imageDAO().insertAll(image);
                }
            }
            Toast.makeText(getApplicationContext(), " Resource saved but with no coordinates", Toast.LENGTH_LONG).show();
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
            double[] location = new double[]{latitude,longitude};
            Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            return location;
        } else {
            locationService.showSettingsAlert();
            return null;
        }
    }
}