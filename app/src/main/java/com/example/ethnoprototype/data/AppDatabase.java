package com.example.ethnoprototype.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UnCategorizedImage.class, UnCategorizedVideo.class, CategoryAndResource.class, CategoryAssignedResource.class},
        version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UnCategorizedImageDAO imageDAO();
    public abstract UnCategorizedVideoDAO videoDAO();
    public abstract CategoryDAO categoryDAO();
    public abstract CategoryAssignedResourceDAO assignedResourceDAO();

    private static AppDatabase INSTANCE;

//    AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//            AppDatabase.class, "database-name").build();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE category "
                    + " ADD COLUMN catID INTEGER");
        }
    };

//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE CategoryAssignedResource "
//                    + " DELETE COLUMN catID INTEGER");
//        }
//    };

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) INSTANCE =
                Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ethno-database")
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
//                        .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;

    }

}
