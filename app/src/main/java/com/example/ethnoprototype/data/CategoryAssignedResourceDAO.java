package com.example.ethnoprototype.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CategoryAssignedResourceDAO {
    @Query("SELECT * FROM CategoryAssignedResource")
    List<CategoryAssignedResource> getAll();

    @Query("SELECT * FROM CategoryAssignedResource WHERE cat_res_id IN (:Ids)")
    List<CategoryAssignedResource> loadAllByIds(int[] Ids);

//    @Query("SELECT * FROM UncategorisedImage WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

//    @Query("SELECT * FROM CategoryAssignedResource WHERE category")

    @Insert
    void insertAll(CategoryAssignedResource... images);

    @Insert
    long insert (CategoryAssignedResource categoryAssignedResource);

    @Delete
    void delete(CategoryAssignedResource image);
}
