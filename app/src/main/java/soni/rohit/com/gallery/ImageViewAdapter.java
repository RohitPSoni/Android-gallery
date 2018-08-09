package soni.rohit.com.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ImageViewHolder> {

    private ArrayList<String>imageList;
    private Context context;
    public ImageViewAdapter(ArrayList<String> imageUrs, Context context){
        this.imageList = imageUrs;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.viwe_holder_image_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ImageViewHolder vh = new ImageViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Log.i("TAG","URL:"+imageList.get(position));
        Picasso.get()
                .load(new File(imageList.get(position)))
                .resize(200, 200)
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageViewHolder(View view){
            super(view);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
