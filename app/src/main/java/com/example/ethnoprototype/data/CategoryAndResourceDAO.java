package com.example.ethnoprototype.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryAndResourceDAO {

    @Query("SELECT * FROM CategoryAndResource")
    List<CategoryAndResource> getAll();

    @Query("SELECT * FROM CategoryAndResource WHERE id IN (:Ids)")
    List<CategoryAndResource> loadAllByIds(int[] Ids);

//    @Query("SELECT * FROM UncategorisedImage WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

//    @Query("SELECT * FROM CategoryAssignedResource WHERE category")

    @Insert
    void insertAll(CategoryAndResource... categoryAndResources);

    @Insert
    long insert (CategoryAndResource categoryAndResource);

    @Delete
    void delete(CategoryAndResource categoryAndResource);
}
