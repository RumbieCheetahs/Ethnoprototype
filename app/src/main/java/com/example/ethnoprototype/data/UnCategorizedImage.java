package com.example.ethnoprototype.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "image")
public class UnCategorizedImage {
    @PrimaryKey(autoGenerate = true)
    public int image_id;
    public String imagePath;
    public Double imageLatitude;
    public Double imageLongitude;
    public String imageDate;
    public String imageTime;

    public UnCategorizedImage(int image_id, String imagePath, Double imageLatitude, Double imageLongitude, String imageDate, String imageTime) {
        this.image_id = image_id;
        this.imagePath = imagePath;
        this.imageLatitude = imageLatitude;
        this.imageLongitude = imageLongitude;
        this.imageDate = imageDate;
        this.imageTime = imageTime;
    }

    public UnCategorizedImage(){

    }


}
