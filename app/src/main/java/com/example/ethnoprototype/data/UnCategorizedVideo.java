package com.example.ethnoprototype.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "video")
public class UnCategorizedVideo implements Parcelable {
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

    protected UnCategorizedVideo(Parcel in) {
        video_id = in.readInt();
        path = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        date = in.readString();
        time = in.readString();
        category = in.readByte() != 0;
    }

    public static final Creator<UnCategorizedVideo> CREATOR = new Creator<UnCategorizedVideo>() {
        @Override
        public UnCategorizedVideo createFromParcel(Parcel in) {
            return new UnCategorizedVideo(in);
        }

        @Override
        public UnCategorizedVideo[] newArray(int size) {
            return new UnCategorizedVideo[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(video_id);
        parcel.writeString(path);
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitude);
        }
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitude);
        }
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeByte((byte) (category ? 1 : 0));
    }
}
