package com.example.ethnoprototype.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UnCategorizedImage.class, UnCategorizedVideo.class, Category.class, CategoryAssignedResource.class},
        version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UnCategorizedImageDAO imageDAO();
    public abstract UnCategorizedVideoDAO videoDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract CategoryAssignedResourceDAO assignedResourceDAO();

    private static AppDatabase INSTANCE;

//    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//            AppDatabase.class, "database-name").build();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) INSTANCE =
                Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ethno-database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;

    }

}
