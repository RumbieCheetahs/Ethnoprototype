package com.example.ethnoprototype.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CategoryAndResource {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public int catID;
    public String name;

    public CategoryAndResource(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryAndResource() {
    }
}
