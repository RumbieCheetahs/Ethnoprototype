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

    public UnCategorizedVideo() {

    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }
}
