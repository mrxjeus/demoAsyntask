package vn.com.example.demoasyntask.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import vn.com.example.demoasyntask.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<File> mPhotos;

    public ImageAdapter(Context context, List<File> photos) {
        this.mContext = context;
        this.mPhotos = photos;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item, null);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Picasso.with(mContext).load(mPhotos.get(position)).fit().centerCrop().into(holder.mImageItem);
    }

    @Override
    public int getItemCount() {
        return mPhotos == null ? 0 : mPhotos.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageItem;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mImageItem = itemView.findViewById(R.id.image_item);
        }
    }
}
