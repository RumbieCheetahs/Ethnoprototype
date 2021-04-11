package com.example.ethnoprototype;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ethnoprototype.data.AppDatabase;
import com.example.ethnoprototype.data.UnCategorizedVideo;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class UncategorisedItemsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_uncategorised_items_activity_list);
        List<UnCategorizedVideo> videoList = AppDatabase.getAppDatabase(UncategorisedItemsActivity.this).videoDAO().getAllWithoutCategory();
        recyclerView = findViewById(R.id.list_uncategorized);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(getBaseContext(),videoList, new MyItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UnCategorizedVideo video) {
                Intent intent = new Intent(getBaseContext(), VideoCategorisaton.class);
                intent.putExtra("path",video.getPath());
                intent.putExtra("latitude",video.getLatitude());
                intent.putExtra("latitude",video.getLatitude());
                intent.putExtra("date",video.getDate());
                intent.putExtra("time",video.getTime());
                intent.putExtra("id",video.video_id);
                intent.putExtra("video",video);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

    }


}