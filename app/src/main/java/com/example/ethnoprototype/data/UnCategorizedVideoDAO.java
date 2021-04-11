package com.example.ethnoprototype.data;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UnCategorizedVideoDAO {
    @Query("SELECT * FROM video")
    List<UnCategorizedVideo> getAll();

    @Query("SELECT * FROM video WHERE video_id IN (:userIds)")
    List<UnCategorizedVideo> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM UncategorisedImage WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);
    @Query("SELECT * FROM video WHERE not category")
    List<UnCategorizedVideo> getAllWithoutCategory();

    @Insert
    void insertAll(UnCategorizedVideo... images);

    @Update
    void update(UnCategorizedVideo video);

    @Delete
    void delete(UnCategorizedVideo image);
}
