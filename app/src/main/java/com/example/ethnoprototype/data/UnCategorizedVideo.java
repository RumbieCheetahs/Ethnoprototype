package com.example.ethnoprototype.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "video")
public class UnCategorizedVideo {
    @PrimaryKey(autoGenerate = true)
    public int video_id;
    public String path;
    public Double latitude;
    public Double longitude;
    public String date;
    public String time;
    public boolean category;

    public UnCategorizedVideo(int video_id, String path, Double latitude, Double longitude, String date, String time, boolean category) {
        this.video_id = video_id;
        this.path = path;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
        this.category = category;
    }
}
