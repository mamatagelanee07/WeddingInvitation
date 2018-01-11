package com.andigeeky.weddinginvitation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andigeeky.weddinginvitation.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryView> {

    private Context context;

    private int[] imgList = {R.drawable.gallery_dark, R.drawable.gallery_dark, R.drawable.gallery_dark, R.drawable.gallery_dark,
            R.drawable.gallery_dark};

    private String[] nameList = {"One", "Two", "Three", "Four", "Five"};

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GalleryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryView(layoutView);
    }

    @Override
    public void onBindViewHolder(GalleryView holder, int position) {
//        holder.imageView.setImageResource(imgList[position]);
        holder.textView.setText(nameList[position]);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    class GalleryView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        GalleryView(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.img_name);
        }
    }
}
