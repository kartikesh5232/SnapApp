package com.example.whatsappclone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class snapAdapter extends RecyclerView.Adapter<snapAdapter.SnapRecycleViewHolder> {


    private Context context;
    private List<Upload> uploads;

    public snapAdapter(Context context, List<Upload> uploads) {
        this.context = context;
        this.uploads = uploads;
    }

    @NonNull
    @Override
    public SnapRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image_item, parent, false);
        return new SnapRecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnapRecycleViewHolder holder, int position) {
        Upload uploaddata = uploads.get(position);
        Picasso.get().load(uploaddata.getUrl()).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    public class SnapRecycleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public SnapRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.snapimage);
        }
    }
}
