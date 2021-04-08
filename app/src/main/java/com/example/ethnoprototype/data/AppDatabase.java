package com.example.ethnoprototype.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UnCategorizedImage.class, UnCategorizedVideo.class, Category.class, CategoryAssignedResource.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UnCategorizedImageDAO imageDAO();
    public abstract UnCategorizedVideoDAO videoDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract CategoryAssignedResourceDAO assignedResourceDAO();

}
