package com.example.ethnoprototype.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UnCategorizedImageDAO {

    @Query("SELECT * FROM image")
    List<UnCategorizedImage> getAll();

    @Query("SELECT * FROM image WHERE image_id IN (:Ids)")
    List<UnCategorizedImage> loadAllByIds(int[] Ids);

//    @Query("SELECT * FROM UncategorisedImage WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

    @Insert
    void insert(UnCategorizedImage image);

    @Delete
    void delete(UnCategorizedImage image);
}
