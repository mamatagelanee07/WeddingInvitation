package com.andigeeky.weddinginvitation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andigeeky.weddinginvitation.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryView> {

    private Context context;
    private List<DocumentSnapshot> documents;


    public GalleryAdapter(Context context, List<DocumentSnapshot> documents) {
        this.context = context;
        this.documents = documents;
    }

    @Override
    public GalleryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new GalleryView(layoutView);
    }

    @Override
    public void onBindViewHolder(GalleryView holder, int position) {

        holder.textView.setText(documents.get(position).getString("name"));
        Glide.with(context).load(documents.get(position).getString("url"))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return documents.size();
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
