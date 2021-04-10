package com.example.ethnoprototype.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM CategoryAndResource")
    List<CategoryAndResource> getAll();

    @Query("SELECT * FROM CategoryAndResource WHERE id IN (:userIds)")
    List<CategoryAndResource> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM UncategorisedImage WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

    @Insert
    void insertAll(CategoryAndResource... categories);

    @Delete
    void delete(CategoryAndResource categoryAndResource);
}
