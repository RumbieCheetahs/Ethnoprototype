package com.example.ethnoprototype;

import android.content.Intent;
import android.os.Bundle;

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

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UncategorisedItemsActivity() {
    }



    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
//    public static UncategorisedItemsActivity newInstance(int columnCount) {
//        UncategorisedItemsActivity fragment = new UncategorisedItemsActivity();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_uncategorised_items_activity_list);
        List<UnCategorizedVideo> videoList = AppDatabase.getAppDatabase(UncategorisedItemsActivity.this).videoDAO().getAll();
        recyclerView = findViewById(R.id.list_uncategorized);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        MyItemRecyclerViewAdapter adapter = new MyItemRecyclerViewAdapter(videoList, new MyItemRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UnCategorizedVideo video) {
                Intent intent = new Intent(getBaseContext(), VideoCategorisaton.class);
                intent.putExtra("path",video.getPath());
                intent.putExtra("latitude",video.getLatitude());
                intent.putExtra("latitude",video.getLatitude());
                intent.putExtra("date",video.getDate());
                intent.putExtra("time",video.getTime());
                intent.putExtra("id",video.getVideo_id());
//                intent.putExtra("item_produce_cycle",model.getCapacityCycle());
//                intent.putExtra("video",video);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_uncategorised_items_activity_list, container, false);
//
//        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS));
//        }
//        return view;
//    }
}