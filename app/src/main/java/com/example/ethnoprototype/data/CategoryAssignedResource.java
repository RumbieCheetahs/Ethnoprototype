package com.example.ethnoprototype.data;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryAssignedResource {
    @PrimaryKey (autoGenerate = true)
    public int cat_res_id;
    public String plantName;
    @Embedded public UnCategorizedImage imageId;
    @Embedded public UnCategorizedVideo videoId;
//    public List<Category> categoryList;
    @Embedded public Category category;



    public CategoryAssignedResource(int cat_res_id, String plantName, UnCategorizedImage imageId, UnCategorizedVideo videoId, Category category) {
        this.cat_res_id = cat_res_id;
        this.plantName = plantName;
        this.imageId = imageId;
        this.videoId = videoId;
        this.category = category;
    }
}
