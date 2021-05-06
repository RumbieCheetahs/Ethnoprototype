package com.example.ethnoprototype;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ethnoprototype.data.UnCategorizedVideo;
import com.example.ethnoprototype.dummy.DummyContent.DummyItem;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(UnCategorizedVideo video);
    }

    private final OnItemClickListener listener ;
    private final Context context;

    private final List<UnCategorizedVideo> mValues;

    public MyItemRecyclerViewAdapter(Context context,List<UnCategorizedVideo> items, OnItemClickListener listener) {
        mValues = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_uncategorised_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.imageView.setText(mValues.get(position).id);
        holder.mContentView.setText( mValues.get(position).date);
        holder.time.setText("");
        Glide.with(context)
                .load(mValues.get(position).path)
                .centerCrop()
                .into(holder.imageView);
        holder.bind(holder.mItem, listener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageView;
        public final TextView time;
        public final TextView mContentView;
        public UnCategorizedVideo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = (ImageView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            time = view.findViewById(R.id.itemTime);
        }

        public void bind(final UnCategorizedVideo video, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(video);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}