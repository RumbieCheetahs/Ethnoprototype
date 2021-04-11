package com.example.ethnoprototype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.CategoryAndResource;
import com.example.ethnoprototype.data.CategoryAssignedResource;
import com.example.ethnoprototype.dummy.DummyContent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class CategoryListActivity extends AppCompatActivity {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    AppDatabase db;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryListActivity() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState,  PersistableBundle persistentState) {
        setContentView(R.layout.fragment_category_list_activity_list);

        recyclerView = findViewById(R.id.categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        db = AppDatabase.getAppDatabase(getBaseContext());
        List<CategoryAndResource> assignedResources = db.categoryAndResourceDAO().getAll();

        HashSet<String> categorySet = new HashSet<>();

        for(CategoryAndResource resource : assignedResources){
            categorySet.add(resource.name);
        }

        List<String> list = new ArrayList<>(categorySet);

        MyCategoryListRecyclerViewAdapter adapter = new MyCategoryListRecyclerViewAdapter(list, new MyCategoryListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String category) {
            //Get all the categoriesIds == category
                //Get all CategoryAssignedResources
                // Send to next activity
                List<Integer> ids = new ArrayList<>();
                for(CategoryAndResource resource : assignedResources){
                    if(resource.name.trim().equalsIgnoreCase(category)){
                        ids.add(resource.catID);
                    }
                }
               int [] idArray = new int[ids.size()];
                for(int i = 0; i<idArray.length; i++){
                    idArray[i] = ids.get(i);
                }
                Intent intent = new Intent(CategoryListActivity.this, ShowResourceActivity.class);
                intent.putExtra("ids",idArray);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }




}