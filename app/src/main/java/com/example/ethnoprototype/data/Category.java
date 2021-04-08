package com.example.ethnoprototype.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey (autoGenerate = true)
    public int id;
    public String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
