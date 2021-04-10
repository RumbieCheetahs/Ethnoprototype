package com.example.ethnoprototype.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryAndResource {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public int catID;
    public String name;

    public CategoryAndResource( String name, int catID) {
        this.name = name;
        this.catID = catID;
    }

    public CategoryAndResource() {
    }
}
